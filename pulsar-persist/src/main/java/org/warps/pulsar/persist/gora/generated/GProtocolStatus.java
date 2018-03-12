/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.warps.pulsar.persist.gora.generated;  

public class GProtocolStatus extends org.apache.gora.persistency.impl.PersistentBase implements org.apache.avro.specific.SpecificRecord, org.apache.gora.persistency.Persistent {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"GProtocolStatus\",\"namespace\":\"org.warps.pulsar.persist.gora.generated\",\"fields\":[{\"name\":\"majorCode\",\"type\":\"int\",\"default\":0},{\"name\":\"minorCode\",\"type\":\"int\",\"default\":0},{\"name\":\"args\",\"type\":{\"type\":\"map\",\"values\":[\"null\",\"string\"]},\"default\":{}}]}");
  private static final long serialVersionUID = -9077714532483792267L;
  /** Enum containing all data bean's fields. */
  public static enum Field {
    MAJOR_CODE(0, "majorCode"),
    MINOR_CODE(1, "minorCode"),
    ARGS(2, "args"),
    ;
    /**
     * Field's index.
     */
    private int index;

    /**
     * Field's name.
     */
    private String name;

    /**
     * Field's constructor
     * @param index field's index.
     * @param name field's name.
     */
    Field(int index, String name) {this.index=index;this.name=name;}

    /**
     * Gets field's index.
     * @return int field's index.
     */
    public int getIndex() {return index;}

    /**
     * Gets field's name.
     * @return String field's name.
     */
    public String getName() {return name;}

    /**
     * Gets field's attributes to string.
     * @return String field's attributes to string.
     */
    public String toString() {return name;}
  };

  public static final String[] _ALL_FIELDS = {
  "majorCode",
  "minorCode",
  "args",
  };

  /**
   * Gets the total field count.
   * @return int field count
   */
  public int getFieldsCount() {
    return GProtocolStatus._ALL_FIELDS.length;
  }

  private int majorCode;
  private int minorCode;
  private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> args;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return majorCode;
    case 1: return minorCode;
    case 2: return args;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value) {
    switch (field$) {
    case 0: majorCode = (java.lang.Integer)(value); break;
    case 1: minorCode = (java.lang.Integer)(value); break;
    case 2: args = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>)((value instanceof org.apache.gora.persistency.Dirtyable) ? value : new org.apache.gora.persistency.impl.DirtyMapWrapper((java.util.Map)value)); break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'majorCode' field.
   */
  public java.lang.Integer getMajorCode() {
    return majorCode;
  }

  /**
   * Sets the value of the 'majorCode' field.
   * @param value the value to set.
   */
  public void setMajorCode(java.lang.Integer value) {
    this.majorCode = value;
    setDirty(0);
  }
  
  /**
   * Checks the dirty status of the 'majorCode' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isMajorCodeDirty() {
    return isDirty(0);
  }

  /**
   * Gets the value of the 'minorCode' field.
   */
  public java.lang.Integer getMinorCode() {
    return minorCode;
  }

  /**
   * Sets the value of the 'minorCode' field.
   * @param value the value to set.
   */
  public void setMinorCode(java.lang.Integer value) {
    this.minorCode = value;
    setDirty(1);
  }
  
  /**
   * Checks the dirty status of the 'minorCode' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isMinorCodeDirty() {
    return isDirty(1);
  }

  /**
   * Gets the value of the 'args' field.
   */
  public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getArgs() {
    return args;
  }

  /**
   * Sets the value of the 'args' field.
   * @param value the value to set.
   */
  public void setArgs(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
    this.args = (value instanceof org.apache.gora.persistency.Dirtyable) ? value : new org.apache.gora.persistency.impl.DirtyMapWrapper(value);
    setDirty(2);
  }
  
  /**
   * Checks the dirty status of the 'args' field. A field is dirty if it represents a change that has not yet been written to the database.
   * @param value the value to set.
   */
  public boolean isArgsDirty() {
    return isDirty(2);
  }

  /** Creates a new GProtocolStatus RecordBuilder */
  public static org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder newBuilder() {
    return new org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder();
  }
  
  /** Creates a new GProtocolStatus RecordBuilder by copying an existing Builder */
  public static org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder newBuilder(org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder other) {
    return new org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder(other);
  }
  
  /** Creates a new GProtocolStatus RecordBuilder by copying an existing GProtocolStatus instance */
  public static org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder newBuilder(org.warps.pulsar.persist.gora.generated.GProtocolStatus other) {
    return new org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder(other);
  }
  
  private static java.nio.ByteBuffer deepCopyToReadOnlyBuffer(
      java.nio.ByteBuffer input) {
    java.nio.ByteBuffer copy = java.nio.ByteBuffer.allocate(input.capacity());
    int position = input.position();
    input.reset();
    int mark = input.position();
    int limit = input.limit();
    input.rewind();
    input.limit(input.capacity());
    copy.put(input);
    input.rewind();
    copy.rewind();
    input.position(mark);
    input.mark();
    copy.position(mark);
    copy.mark();
    input.position(position);
    copy.position(position);
    input.limit(limit);
    copy.limit(limit);
    return copy.asReadOnlyBuffer();
  }
  
  /**
   * RecordBuilder for GProtocolStatus instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<GProtocolStatus>
    implements org.apache.avro.data.RecordBuilder<GProtocolStatus> {

    private int majorCode;
    private int minorCode;
    private java.util.Map<java.lang.CharSequence,java.lang.CharSequence> args;

    /** Creates a new Builder */
    private Builder() {
      super(org.warps.pulsar.persist.gora.generated.GProtocolStatus.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing GProtocolStatus instance */
    private Builder(org.warps.pulsar.persist.gora.generated.GProtocolStatus other) {
            super(org.warps.pulsar.persist.gora.generated.GProtocolStatus.SCHEMA$);
      if (isValidValue(fields()[0], other.majorCode)) {
        this.majorCode = (java.lang.Integer) data().deepCopy(fields()[0].schema(), other.majorCode);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.minorCode)) {
        this.minorCode = (java.lang.Integer) data().deepCopy(fields()[1].schema(), other.minorCode);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.args)) {
        this.args = (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) data().deepCopy(fields()[2].schema(), other.args);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'majorCode' field */
    public java.lang.Integer getMajorCode() {
      return majorCode;
    }
    
    /** Sets the value of the 'majorCode' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder setMajorCode(int value) {
      validate(fields()[0], value);
      this.majorCode = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'majorCode' field has been set */
    public boolean hasMajorCode() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'majorCode' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder clearMajorCode() {
      fieldSetFlags()[0] = false;
      return this;
    }
    
    /** Gets the value of the 'minorCode' field */
    public java.lang.Integer getMinorCode() {
      return minorCode;
    }
    
    /** Sets the value of the 'minorCode' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder setMinorCode(int value) {
      validate(fields()[1], value);
      this.minorCode = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'minorCode' field has been set */
    public boolean hasMinorCode() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'minorCode' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder clearMinorCode() {
      fieldSetFlags()[1] = false;
      return this;
    }
    
    /** Gets the value of the 'args' field */
    public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getArgs() {
      return args;
    }
    
    /** Sets the value of the 'args' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder setArgs(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
      validate(fields()[2], value);
      this.args = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'args' field has been set */
    public boolean hasArgs() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'args' field */
    public org.warps.pulsar.persist.gora.generated.GProtocolStatus.Builder clearArgs() {
      args = null;
      fieldSetFlags()[2] = false;
      return this;
    }
    
    @Override
    public GProtocolStatus build() {
      try {
        GProtocolStatus record = new GProtocolStatus();
        record.majorCode = fieldSetFlags()[0] ? this.majorCode : (java.lang.Integer) defaultValue(fields()[0]);
        record.minorCode = fieldSetFlags()[1] ? this.minorCode : (java.lang.Integer) defaultValue(fields()[1]);
        record.args = fieldSetFlags()[2] ? this.args : (java.util.Map<java.lang.CharSequence,java.lang.CharSequence>) new org.apache.gora.persistency.impl.DirtyMapWrapper((java.util.Map)defaultValue(fields()[2]));
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
  
  public GProtocolStatus.Tombstone getTombstone(){
  	return TOMBSTONE;
  }

  public GProtocolStatus newInstance(){
    return newBuilder().build();
  }

  private static final Tombstone TOMBSTONE = new Tombstone();
  
  public static final class Tombstone extends GProtocolStatus implements org.apache.gora.persistency.Tombstone {
  
      private Tombstone() { }
  
	  		  /**
	   * Gets the value of the 'majorCode' field.
		   */
	  public java.lang.Integer getMajorCode() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'majorCode' field.
		   * @param value the value to set.
	   */
	  public void setMajorCode(java.lang.Integer value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'majorCode' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isMajorCodeDirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
				  /**
	   * Gets the value of the 'minorCode' field.
		   */
	  public java.lang.Integer getMinorCode() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'minorCode' field.
		   * @param value the value to set.
	   */
	  public void setMinorCode(java.lang.Integer value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'minorCode' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isMinorCodeDirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
				  /**
	   * Gets the value of the 'args' field.
		   */
	  public java.util.Map<java.lang.CharSequence,java.lang.CharSequence> getArgs() {
	    throw new java.lang.UnsupportedOperationException("Get is not supported on tombstones");
	  }
	
	  /**
	   * Sets the value of the 'args' field.
		   * @param value the value to set.
	   */
	  public void setArgs(java.util.Map<java.lang.CharSequence,java.lang.CharSequence> value) {
	    throw new java.lang.UnsupportedOperationException("Set is not supported on tombstones");
	  }
	  
	  /**
	   * Checks the dirty status of the 'args' field. A field is dirty if it represents a change that has not yet been written to the database.
		   * @param value the value to set.
	   */
	  public boolean isArgsDirty() {
	    throw new java.lang.UnsupportedOperationException("IsDirty is not supported on tombstones");
	  }
	
		  
  }

  private static final org.apache.avro.io.DatumWriter
            DATUM_WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);
  private static final org.apache.avro.io.DatumReader
            DATUM_READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  /**
   * Writes AVRO data bean to output stream in the form of AVRO Binary encoding format. This will transform
   * AVRO data bean from its Java object form to it s serializable form.
   *
   * @param out java.io.ObjectOutput output stream to write data bean in serializable form
   */
  @Override
  public void writeExternal(java.io.ObjectOutput out)
          throws java.io.IOException {
    out.write(super.getDirtyBytes().array());
    DATUM_WRITER$.write(this, org.apache.avro.io.EncoderFactory.get()
            .directBinaryEncoder((java.io.OutputStream) out,
                    null));
  }

  /**
   * Reads AVRO data bean from input stream in it s AVRO Binary encoding format to Java object format.
   * This will transform AVRO data bean from it s serializable form to deserialized Java object form.
   *
   * @param in java.io.ObjectOutput input stream to read data bean in serializable form
   */
  @Override
  public void readExternal(java.io.ObjectInput in)
          throws java.io.IOException {
    byte[] __g__dirty = new byte[getFieldsCount()];
    in.read(__g__dirty);
    super.setDirtyBytes(java.nio.ByteBuffer.wrap(__g__dirty));
    DATUM_READER$.read(this, org.apache.avro.io.DecoderFactory.get()
            .directBinaryDecoder((java.io.InputStream) in,
                    null));
  }
  
}

