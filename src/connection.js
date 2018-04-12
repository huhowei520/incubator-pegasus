/**
 * Created by hyc on 18-2-1
 */

"use strict";

const EventEmitter = require('events').EventEmitter;
const net = require('net');
const util = require('util');

const log = require('../log');
const Exception = require('./errors');

const ErrorCode = require('./dsn/base_types').error_code;
const ErrorType = require('./dsn/base_types').error_type;

const thrift = require('thrift');
const InputBufferUnderrunError = require('thrift/lib/nodejs/lib/thrift/input_buffer_underrun_error');

let _seq_id = 0;

/**
 * Constructor of Connection
 * @param {Object}  options
 *        {String}  options.host            required
 *        {Number}  options.port            required
 *        {Number}  options.rpcTimeOut(ms)  required
 *        {Object}  options.transport       optional
 *        {Object}  options.protocol        optional
 * @constructor
 */
function Connection (options) {
    this.id = ++_seq_id;
    this.host = options.host;
    this.port = options.port;

    this.address = {host: options.host, port: options.port};
    this.hostnamePort = options.host + ':' + options.port;
    this.name = 'Connection(' + this.hostnamePort + ')#' + this.id;
    this.rpcTimeOut = options.rpcTimeOut;

    this.socket = null;
    this.out = null;
    this._connected = false;
    this._requestNums = 0;
    this.requests = {}; //current active requests

    this.transport = options.transport || thrift.TBufferedTransport;
    this.protocol = options.protocol || thrift.TBinaryProtocol;

    EventEmitter.call(this);

    this.setupStream();
}
util.inherits(Connection, EventEmitter);

Connection.Request_counter = 0; //total request for one client

/**
 * Set up connection and register events
 */
Connection.prototype.setupConnection = function(){
    this.socket = net.connect(this.address);
    //register socket event
    this.socket.on('timeout', this._handleTimeout.bind(this));
    this.socket.on('close', this._handleClose.bind(this));
    this.socket.on('error', this._handleError.bind(this));
    //register connection event
    this.on('close', this._close.bind(this));
};

/**
 * Handle socket timeout
 * @private
 */
Connection.prototype._handleTimeout = function(){
    this._close(new Exception.ConnectionClosedException('%s socket closed by timeout', this.name));
};

/**
 * Handle socket close
 * @private
 */
Connection.prototype._handleClose = function(){
    log.debug('%s close event emit', this.name);
    this.closed = true;
    this.emit('close');
    let err = this._closeError || this._socketError;
    if (!err) {
        // err = new Exception.ConnectionClosedException(this.name + ' closed with no error.');
        err = new Exception.RPCException('ERR_SESSION_RESET', this.name + ' closed with no error.');
        log.debug(err.message);
    }
    this._cleanupRequests(err);
};

/**
 * Handle socket error
 * @param err
 * @private
 * @emit {ConnectionClosedException} socket error
 */
Connection.prototype._handleError = function (err) {
    let errorName = 'ConnectionError';
    if (err.message.indexOf('ECONNREFUSED') >= 0) {
        errorName = 'ConnectionRefusedException';
        err = new Exception.RPCException('ERR_SESSION_RESET', this.name + ' error: ' + errorName + ', ' + err.message);
    }else if (err.message.indexOf('ECONNRESET') >= 0 || err.message.indexOf('This socket is closed') >= 0) {
        errorName = 'ConnectionResetException';
        err = new Exception.RPCException('ERR_SESSION_RESET', this.name + ' error: ' + errorName + ', ' + err.message);
    }else{
        err = new Exception.RPCException('ERR_SESSION_RESET', this.name + ' error: ' + errorName + ', ' + err.message);
        // err = new Exception.ConnectionClosedException(this.name + ' error: ' + errorName + ', ' + err.message);
    }

    log.error(err.message);
    this._socketError = err;
    if (!this._connected) { //handle connection closed
        this.connectError = err;
        this.emit('connectError', err);
    }
    this._cleanupRequests(err);
};

/**
 * Clean up all requests of connection
 * @param err
 * @private
 */
Connection.prototype._cleanupRequests = function(err){
    let count = 0;
    let requests = this.requests;
    // if(requests.length === 0){
    //     return;
    // }
    this.requests = {};
    for(let id in requests){
        let request = requests[id];
        request.setException(err);
        count++;
    }
    log.debug('%s cleanup %d requests', this.name, count);
};

/**
 * Close connection and destroy socket
 * @param {ConnectionClosedException} err
 * @private
 */
Connection.prototype._close = function(err){
    this.closed = true;
    this._closeError = err;
    this.socket.destroy();
    log.debug('%s socket destroy', this.name);
};

/**
 * Register data event for socket to receive response
 */
Connection.prototype.getResponse = function(){
    let self = this;
    this.socket.on('data', self.transport.receiver(function(transport_with_data){
        let protocol = new self.protocol(transport_with_data);
        try {
            while (true) {
                let ec = new ErrorCode();

                //Response structure: total length + error code structure + TMessage
                let len = protocol.readI32();
                ec.read(protocol);

                let msgHeader = protocol.readMessageBegin();
                let request = self.requests[msgHeader.rseqid];

                if (request) {
                    let entry = request.entry;
                    entry.operator.rpc_error = ec;
                    if (ErrorType[ec.errno] === ErrorType.ERR_OK) {
                        entry.operator.recv_data(protocol);
                    } else {
                        log.error('Request failed, error code is %s', entry.operator.rpc_error.errno);
                    }
                    transport_with_data.commitPosition();
                    request.setResponse(entry.operator.response);

                } else {
                    log.error('%s Request#%d does not exist, maybe timeout', self.name, msgHeader.rseqid);
                }
            }

        } catch(e){
            if (e instanceof InputBufferUnderrunError) {
                transport_with_data.rollbackPosition();
            }
            else {
                self.socket.emit('error', e);
            }
        }
    }));
};

/**
 * Set up socket stream
 */
Connection.prototype.setupStream = function(){
    let self = this;
    log.debug('Connecting to %s', self.name);

    self.setupConnection();
    self.out = new DataOutputStream(this.socket);

    self.socket.on('connect', function (){
        this._connected = true;
        self.getResponse();
        self.emit('connect');
        log.debug('Connected to %s', self.name);
    });
};

/**
 * Send request and register request events
 * @param entry
 */
Connection.prototype.call = function(entry){
    let timeout = entry.operator.timeout;
    let self = this;
    let connectionRequestId = self._requestNums++;

    let rpcRequest = new Request(this, connectionRequestId, entry, timeout);
    self.requests[rpcRequest.id] = rpcRequest;

    rpcRequest.on('done', function(err, operator){
        delete self.requests[rpcRequest.id];
        if(err){
            log.error(err.message);
        }
        entry.callback(err, operator);
    });

    if(self.closed){
        return this._handleClose();
    }

    rpcRequest.on('timeout', function(){
        delete self.requests[rpcRequest.id];
    });

    this.sendRequest(rpcRequest);
};

/**
 * Send request
 * @param request
 */
Connection.prototype.sendRequest = function(request){
    let transport = new this.transport();
    let protocol = new this.protocol(transport);

    request.entry.operator.send_data(protocol, request.id);

    let headerBuf = request.entry.operator.prepare_thrift_header(transport.outCount);
    transport.outBuffers.unshift(headerBuf);
    transport.outCount += request.entry.operator.header.header_length;
    transport.onFlush = this.send.bind(this);
    transport.flush();
};

/**
 * Write data to socket
 * @param msg
 */
Connection.prototype.send = function(msg){
    this.out.write(msg);
};

/**
 * Swap class of socket
 * @param out
 * @constructor
 */
function DataOutputStream(out){
    this.out = out;
    this.written = 0;
}

/**
 * Write data into stream
 * @param buffer
 */
DataOutputStream.prototype.write = function(buffer){
    this.out.write(buffer);
    this.written += buffer.length;
};


/**
 * Constructor of request
 * @param {Connection}      connection
 * @param {number}          seqid
 * @param {RequestEntry}    entry
 * @param {number}          timeout
 * @constructor
 */
function Request(connection, seqid, entry, timeout) {
    EventEmitter.call(this);

    Connection.Request_counter++;
    this.connection = connection;
    this.id = seqid;    //seq id
    this.entry = entry;
    this.error = null;
    this.done = false;
    this.startTime = Date.now();
    this.timeout = timeout;

    if(timeout && timeout > 0){
        log.debug('%s-request%d, timeout %d', this.connection.name, this.id, this.timeout);
        this.timer = setTimeout(this.handleTimeout.bind(this), parseInt(timeout));
    }
}
util.inherits(Request, EventEmitter);

/**
 * Handle request timeout
 */
Request.prototype.handleTimeout = function(){
    let msg = this.connection.name + ' request#' + this.id + ' timeout, use ' + (Date.now()-this.startTime) + 'ms';
    this.entry.operator.rpc_error = new ErrorCode({'errno' : 'ERR_TIMEOUT'});

    let err = new Exception.RPCException('ERR_TIMEOUT', msg);
    this.setException(err);
    this.emit('timeout');
};

/**
 * Set request exception
 * @param err
 */
Request.prototype.setException = function(err){
    if(err.message.indexOf('no error') < 0) {
        log.error('%s request#%d error %s', this.connection.name, this.id, err.message);
    }
    this.error = err;
    if(err.err_type === 'ERR_SESSION_RESET'){
        this.entry.operator.rpc_error = new ErrorCode({'errno' : 'ERR_SESSION_RESET'});
    }
    this.callComplete();
};

/**
 * Set request completed
 */
Request.prototype.callComplete = function(){
    if(this.timer){
        clearTimeout(this.timer);
        this.timer = null;
    }
    //Done before
    if(this.done){
        return;
    }
    this.done = true;

    let usedTime = Date.now()-this.startTime;
    this.entry.operator.timeout -= usedTime;
    log.debug('%s-request#%d use %dms, remain timeout %dms',
        this.connection.name,
        this.id,
        usedTime,
        this.entry.operator.timeout);
    this.emit('done', this.error, this.entry.operator);
};

/**
 * Set response
 * @param response
 */
Request.prototype.setResponse = function(response){
    this.entry.operator.response = response;
    this.callComplete();
};

module.exports = Connection;