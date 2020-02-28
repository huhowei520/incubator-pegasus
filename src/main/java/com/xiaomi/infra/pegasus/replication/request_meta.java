/**
 * Autogenerated by Thrift
 *
 * <p>DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package com.xiaomi.infra.pegasus.replication;

import com.xiaomi.infra.pegasus.thrift.*;
import com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData;
import com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData;
import com.xiaomi.infra.pegasus.thrift.protocol.*;
import com.xiaomi.infra.pegasus.thrift.scheme.IScheme;
import com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory;
import com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme;
import com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme;
import com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class request_meta
    implements TBase<request_meta, request_meta._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<request_meta> {
  private static final TStruct STRUCT_DESC = new TStruct("meta");

  private static final TField APP_ID_FIELD_DESC = new TField("app_id", TType.I32, (short) 1);
  private static final TField PARTITION_INDEX_FIELD_DESC =
      new TField("partition_index", TType.I32, (short) 2);
  private static final TField CLIENT_TIMEOUT_FIELD_DESC =
      new TField("client_timeout", TType.I32, (short) 3);
  private static final TField PARTITION_HASH_FIELD_DESC =
      new TField("partition_hash", TType.I64, (short) 4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes =
      new HashMap<Class<? extends IScheme>, SchemeFactory>();

  static {
    schemes.put(StandardScheme.class, new metaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new metaTupleSchemeFactory());
  }

  public int app_id; // required
  public int partition_index; // required
  public int client_timeout; // required
  public long partition_hash; // required

  /**
   * The set of fields this struct contains, along with convenience methods for finding and
   * manipulating them.
   */
  public enum _Fields implements TFieldIdEnum {
    APP_ID((short) 1, "app_id"),
    PARTITION_INDEX((short) 2, "partition_index"),
    CLIENT_TIMEOUT((short) 3, "client_timeout"),
    PARTITION_HASH((short) 4, "partition_hash");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /** Find the _Fields constant that matches fieldId, or null if its not found. */
    public static _Fields findByThriftId(int fieldId) {
      switch (fieldId) {
        case 1: // APP_ID
          return APP_ID;
        case 2: // PARTITION_INDEX
          return PARTITION_INDEX;
        case 3: // CLIENT_TIMEOUT
          return CLIENT_TIMEOUT;
        case 4: // PARTITION_HASH
          return PARTITION_HASH;
        default:
          return null;
      }
    }

    /** Find the _Fields constant that matches fieldId, throwing an exception if it is not found. */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null)
        throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /** Find the _Fields constant that matches name, or null if its not found. */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __APP_ID_ISSET_ID = 0;
  private static final int __PARTITION_INDEX_ISSET_ID = 1;
  private static final int __CLIENT_TIMEOUT_ISSET_ID = 2;
  private static final int __PARTITION_HASH_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, FieldMetaData> metaDataMap;

  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(
        _Fields.APP_ID,
        new FieldMetaData(
            "app_id", TFieldRequirementType.DEFAULT, new FieldValueMetaData(TType.I32)));
    tmpMap.put(
        _Fields.PARTITION_INDEX,
        new FieldMetaData(
            "partition_index", TFieldRequirementType.DEFAULT, new FieldValueMetaData(TType.I32)));
    tmpMap.put(
        _Fields.CLIENT_TIMEOUT,
        new FieldMetaData(
            "client_timeout", TFieldRequirementType.DEFAULT, new FieldValueMetaData(TType.I32)));
    tmpMap.put(
        _Fields.PARTITION_HASH,
        new FieldMetaData(
            "partition_hash", TFieldRequirementType.DEFAULT, new FieldValueMetaData(TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(request_meta.class, metaDataMap);
  }

  public request_meta() {}

  public request_meta(int app_id, int partition_index, int client_timeout, long partition_hash) {
    this();
    this.app_id = app_id;
    setApp_idIsSet(true);
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    this.client_timeout = client_timeout;
    setClient_timeoutIsSet(true);
    this.partition_hash = partition_hash;
    setPartition_hashIsSet(true);
  }

  /** Performs a deep copy on <i>other</i>. */
  public request_meta(request_meta other) {
    __isset_bitfield = other.__isset_bitfield;
    this.app_id = other.app_id;
    this.partition_index = other.partition_index;
    this.client_timeout = other.client_timeout;
    this.partition_hash = other.partition_hash;
  }

  public request_meta deepCopy() {
    return new request_meta(this);
  }

  @Override
  public void clear() {
    setApp_idIsSet(false);
    this.app_id = 0;
    setPartition_indexIsSet(false);
    this.partition_index = 0;
    setClient_timeoutIsSet(false);
    this.client_timeout = 0;
    setPartition_hashIsSet(false);
    this.partition_hash = 0;
  }

  public int getApp_id() {
    return this.app_id;
  }

  public request_meta setApp_id(int app_id) {
    this.app_id = app_id;
    setApp_idIsSet(true);
    return this;
  }

  public void unsetApp_id() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __APP_ID_ISSET_ID);
  }

  /** Returns true if field app_id is set (has been assigned a value) and false otherwise */
  public boolean isSetApp_id() {
    return EncodingUtils.testBit(__isset_bitfield, __APP_ID_ISSET_ID);
  }

  public void setApp_idIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __APP_ID_ISSET_ID, value);
  }

  public int getPartition_index() {
    return this.partition_index;
  }

  public request_meta setPartition_index(int partition_index) {
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    return this;
  }

  public void unsetPartition_index() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  /**
   * Returns true if field partition_index is set (has been assigned a value) and false otherwise
   */
  public boolean isSetPartition_index() {
    return EncodingUtils.testBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  public void setPartition_indexIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID, value);
  }

  public int getClient_timeout() {
    return this.client_timeout;
  }

  public request_meta setClient_timeout(int client_timeout) {
    this.client_timeout = client_timeout;
    setClient_timeoutIsSet(true);
    return this;
  }

  public void unsetClient_timeout() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID);
  }

  /** Returns true if field client_timeout is set (has been assigned a value) and false otherwise */
  public boolean isSetClient_timeout() {
    return EncodingUtils.testBit(__isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID);
  }

  public void setClient_timeoutIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID, value);
  }

  public long getPartition_hash() {
    return this.partition_hash;
  }

  public request_meta setPartition_hash(long partition_hash) {
    this.partition_hash = partition_hash;
    setPartition_hashIsSet(true);
    return this;
  }

  public void unsetPartition_hash() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PARTITION_HASH_ISSET_ID);
  }

  /** Returns true if field partition_hash is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition_hash() {
    return EncodingUtils.testBit(__isset_bitfield, __PARTITION_HASH_ISSET_ID);
  }

  public void setPartition_hashIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PARTITION_HASH_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
      case APP_ID:
        if (value == null) {
          unsetApp_id();
        } else {
          setApp_id((Integer) value);
        }
        break;

      case PARTITION_INDEX:
        if (value == null) {
          unsetPartition_index();
        } else {
          setPartition_index((Integer) value);
        }
        break;

      case CLIENT_TIMEOUT:
        if (value == null) {
          unsetClient_timeout();
        } else {
          setClient_timeout((Integer) value);
        }
        break;

      case PARTITION_HASH:
        if (value == null) {
          unsetPartition_hash();
        } else {
          setPartition_hash((Long) value);
        }
        break;
    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
      case APP_ID:
        return getApp_id();

      case PARTITION_INDEX:
        return getPartition_index();

      case CLIENT_TIMEOUT:
        return getClient_timeout();

      case PARTITION_HASH:
        return getPartition_hash();
    }
    throw new IllegalStateException();
  }

  /**
   * Returns true if field corresponding to fieldID is set (has been assigned a value) and false
   * otherwise
   */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
      case APP_ID:
        return isSetApp_id();
      case PARTITION_INDEX:
        return isSetPartition_index();
      case CLIENT_TIMEOUT:
        return isSetClient_timeout();
      case PARTITION_HASH:
        return isSetPartition_hash();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null) return false;
    if (that instanceof request_meta) return this.equals((request_meta) that);
    return false;
  }

  public boolean equals(request_meta that) {
    if (that == null) return false;

    boolean this_present_app_id = true;
    boolean that_present_app_id = true;
    if (this_present_app_id || that_present_app_id) {
      if (!(this_present_app_id && that_present_app_id)) return false;
      if (this.app_id != that.app_id) return false;
    }

    boolean this_present_partition_index = true;
    boolean that_present_partition_index = true;
    if (this_present_partition_index || that_present_partition_index) {
      if (!(this_present_partition_index && that_present_partition_index)) return false;
      if (this.partition_index != that.partition_index) return false;
    }

    boolean this_present_client_timeout = true;
    boolean that_present_client_timeout = true;
    if (this_present_client_timeout || that_present_client_timeout) {
      if (!(this_present_client_timeout && that_present_client_timeout)) return false;
      if (this.client_timeout != that.client_timeout) return false;
    }

    boolean this_present_partition_hash = true;
    boolean that_present_partition_hash = true;
    if (this_present_partition_hash || that_present_partition_hash) {
      if (!(this_present_partition_hash && that_present_partition_hash)) return false;
      if (this.partition_hash != that.partition_hash) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_app_id = true;
    list.add(present_app_id);
    if (present_app_id) list.add(app_id);

    boolean present_partition_index = true;
    list.add(present_partition_index);
    if (present_partition_index) list.add(partition_index);

    boolean present_client_timeout = true;
    list.add(present_client_timeout);
    if (present_client_timeout) list.add(client_timeout);

    boolean present_partition_hash = true;
    list.add(present_partition_hash);
    if (present_partition_hash) list.add(partition_hash);

    return list.hashCode();
  }

  @Override
  public int compareTo(request_meta other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetApp_id()).compareTo(other.isSetApp_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApp_id()) {
      lastComparison = TBaseHelper.compareTo(this.app_id, other.app_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison =
        Boolean.valueOf(isSetPartition_index()).compareTo(other.isSetPartition_index());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_index()) {
      lastComparison = TBaseHelper.compareTo(this.partition_index, other.partition_index);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClient_timeout()).compareTo(other.isSetClient_timeout());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient_timeout()) {
      lastComparison = TBaseHelper.compareTo(this.client_timeout, other.client_timeout);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPartition_hash()).compareTo(other.isSetPartition_hash());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_hash()) {
      lastComparison = TBaseHelper.compareTo(this.partition_hash, other.partition_hash);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("meta(");
    boolean first = true;

    sb.append("app_id:");
    sb.append(this.app_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_index:");
    sb.append(this.partition_index);
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_timeout:");
    sb.append(this.client_timeout);
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_hash:");
    sb.append(this.partition_hash);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new TCompactProtocol(new TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in)
      throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and
      // doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new TCompactProtocol(new TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class metaStandardSchemeFactory implements SchemeFactory {
    public metaStandardScheme getScheme() {
      return new metaStandardScheme();
    }
  }

  private static class metaStandardScheme extends StandardScheme<request_meta> {

    public void read(TProtocol iprot, request_meta struct) throws TException {
      TField schemeField;
      iprot.readStructBegin();
      while (true) {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // APP_ID
            if (schemeField.type == TType.I32) {
              struct.app_id = iprot.readI32();
              struct.setApp_idIsSet(true);
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARTITION_INDEX
            if (schemeField.type == TType.I32) {
              struct.partition_index = iprot.readI32();
              struct.setPartition_indexIsSet(true);
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_TIMEOUT
            if (schemeField.type == TType.I32) {
              struct.client_timeout = iprot.readI32();
              struct.setClient_timeoutIsSet(true);
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PARTITION_HASH
            if (schemeField.type == TType.I64) {
              struct.partition_hash = iprot.readI64();
              struct.setPartition_hashIsSet(true);
            } else {
              TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(TProtocol oprot, request_meta struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(APP_ID_FIELD_DESC);
      oprot.writeI32(struct.app_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARTITION_INDEX_FIELD_DESC);
      oprot.writeI32(struct.partition_index);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CLIENT_TIMEOUT_FIELD_DESC);
      oprot.writeI32(struct.client_timeout);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARTITION_HASH_FIELD_DESC);
      oprot.writeI64(struct.partition_hash);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class metaTupleSchemeFactory implements SchemeFactory {
    public metaTupleScheme getScheme() {
      return new metaTupleScheme();
    }
  }

  private static class metaTupleScheme extends TupleScheme<request_meta> {

    @Override
    public void write(TProtocol prot, request_meta struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetApp_id()) {
        optionals.set(0);
      }
      if (struct.isSetPartition_index()) {
        optionals.set(1);
      }
      if (struct.isSetClient_timeout()) {
        optionals.set(2);
      }
      if (struct.isSetPartition_hash()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetApp_id()) {
        oprot.writeI32(struct.app_id);
      }
      if (struct.isSetPartition_index()) {
        oprot.writeI32(struct.partition_index);
      }
      if (struct.isSetClient_timeout()) {
        oprot.writeI32(struct.client_timeout);
      }
      if (struct.isSetPartition_hash()) {
        oprot.writeI64(struct.partition_hash);
      }
    }

    @Override
    public void read(TProtocol prot, request_meta struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.app_id = iprot.readI32();
        struct.setApp_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.partition_index = iprot.readI32();
        struct.setPartition_indexIsSet(true);
      }
      if (incoming.get(2)) {
        struct.client_timeout = iprot.readI32();
        struct.setClient_timeoutIsSet(true);
      }
      if (incoming.get(3)) {
        struct.partition_hash = iprot.readI64();
        struct.setPartition_hashIsSet(true);
      }
    }
  }
}
