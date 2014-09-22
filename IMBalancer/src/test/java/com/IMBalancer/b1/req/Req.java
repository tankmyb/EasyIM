// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ReqData.proto

package com.IMBalancer.b1.req;

public final class Req {
  private Req() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface ReqDataOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required string data = 1;
    /**
     * <code>required string data = 1;</code>
     */
    boolean hasData();
    /**
     * <code>required string data = 1;</code>
     */
    java.lang.String getData();
    /**
     * <code>required string data = 1;</code>
     */
    com.google.protobuf.ByteString
        getDataBytes();

    // required string data2 = 2;
    /**
     * <code>required string data2 = 2;</code>
     */
    boolean hasData2();
    /**
     * <code>required string data2 = 2;</code>
     */
    java.lang.String getData2();
    /**
     * <code>required string data2 = 2;</code>
     */
    com.google.protobuf.ByteString
        getData2Bytes();
  }
  /**
   * Protobuf type {@code com.IMBalancer.b1.req.ReqData}
   */
  public static final class ReqData extends
      com.google.protobuf.GeneratedMessage
      implements ReqDataOrBuilder {
    // Use ReqData.newBuilder() to construct.
    private ReqData(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private ReqData(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final ReqData defaultInstance;
    public static ReqData getDefaultInstance() {
      return defaultInstance;
    }

    public ReqData getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private ReqData(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              data_ = input.readBytes();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              data2_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.IMBalancer.b1.req.Req.internal_static_com_IMBalancer_b1_req_ReqData_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.IMBalancer.b1.req.Req.internal_static_com_IMBalancer_b1_req_ReqData_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.IMBalancer.b1.req.Req.ReqData.class, com.IMBalancer.b1.req.Req.ReqData.Builder.class);
    }

    public static com.google.protobuf.Parser<ReqData> PARSER =
        new com.google.protobuf.AbstractParser<ReqData>() {
      public ReqData parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ReqData(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<ReqData> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string data = 1;
    public static final int DATA_FIELD_NUMBER = 1;
    private java.lang.Object data_;
    /**
     * <code>required string data = 1;</code>
     */
    public boolean hasData() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string data = 1;</code>
     */
    public java.lang.String getData() {
      java.lang.Object ref = data_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          data_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string data = 1;</code>
     */
    public com.google.protobuf.ByteString
        getDataBytes() {
      java.lang.Object ref = data_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        data_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required string data2 = 2;
    public static final int DATA2_FIELD_NUMBER = 2;
    private java.lang.Object data2_;
    /**
     * <code>required string data2 = 2;</code>
     */
    public boolean hasData2() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string data2 = 2;</code>
     */
    public java.lang.String getData2() {
      java.lang.Object ref = data2_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          data2_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string data2 = 2;</code>
     */
    public com.google.protobuf.ByteString
        getData2Bytes() {
      java.lang.Object ref = data2_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        data2_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      data_ = "";
      data2_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasData()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasData2()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getDataBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getData2Bytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getDataBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getData2Bytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.IMBalancer.b1.req.Req.ReqData parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.IMBalancer.b1.req.Req.ReqData prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.IMBalancer.b1.req.ReqData}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.IMBalancer.b1.req.Req.ReqDataOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.IMBalancer.b1.req.Req.internal_static_com_IMBalancer_b1_req_ReqData_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.IMBalancer.b1.req.Req.internal_static_com_IMBalancer_b1_req_ReqData_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.IMBalancer.b1.req.Req.ReqData.class, com.IMBalancer.b1.req.Req.ReqData.Builder.class);
      }

      // Construct using com.IMBalancer.b1.req.Req.ReqData.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        data_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        data2_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.IMBalancer.b1.req.Req.internal_static_com_IMBalancer_b1_req_ReqData_descriptor;
      }

      public com.IMBalancer.b1.req.Req.ReqData getDefaultInstanceForType() {
        return com.IMBalancer.b1.req.Req.ReqData.getDefaultInstance();
      }

      public com.IMBalancer.b1.req.Req.ReqData build() {
        com.IMBalancer.b1.req.Req.ReqData result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.IMBalancer.b1.req.Req.ReqData buildPartial() {
        com.IMBalancer.b1.req.Req.ReqData result = new com.IMBalancer.b1.req.Req.ReqData(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.data_ = data_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.data2_ = data2_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.IMBalancer.b1.req.Req.ReqData) {
          return mergeFrom((com.IMBalancer.b1.req.Req.ReqData)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.IMBalancer.b1.req.Req.ReqData other) {
        if (other == com.IMBalancer.b1.req.Req.ReqData.getDefaultInstance()) return this;
        if (other.hasData()) {
          bitField0_ |= 0x00000001;
          data_ = other.data_;
          onChanged();
        }
        if (other.hasData2()) {
          bitField0_ |= 0x00000002;
          data2_ = other.data2_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasData()) {
          
          return false;
        }
        if (!hasData2()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.IMBalancer.b1.req.Req.ReqData parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.IMBalancer.b1.req.Req.ReqData) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required string data = 1;
      private java.lang.Object data_ = "";
      /**
       * <code>required string data = 1;</code>
       */
      public boolean hasData() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string data = 1;</code>
       */
      public java.lang.String getData() {
        java.lang.Object ref = data_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          data_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string data = 1;</code>
       */
      public com.google.protobuf.ByteString
          getDataBytes() {
        java.lang.Object ref = data_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          data_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string data = 1;</code>
       */
      public Builder setData(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string data = 1;</code>
       */
      public Builder clearData() {
        bitField0_ = (bitField0_ & ~0x00000001);
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }
      /**
       * <code>required string data = 1;</code>
       */
      public Builder setDataBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        data_ = value;
        onChanged();
        return this;
      }

      // required string data2 = 2;
      private java.lang.Object data2_ = "";
      /**
       * <code>required string data2 = 2;</code>
       */
      public boolean hasData2() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string data2 = 2;</code>
       */
      public java.lang.String getData2() {
        java.lang.Object ref = data2_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          data2_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string data2 = 2;</code>
       */
      public com.google.protobuf.ByteString
          getData2Bytes() {
        java.lang.Object ref = data2_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          data2_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string data2 = 2;</code>
       */
      public Builder setData2(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        data2_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string data2 = 2;</code>
       */
      public Builder clearData2() {
        bitField0_ = (bitField0_ & ~0x00000002);
        data2_ = getDefaultInstance().getData2();
        onChanged();
        return this;
      }
      /**
       * <code>required string data2 = 2;</code>
       */
      public Builder setData2Bytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        data2_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.IMBalancer.b1.req.ReqData)
    }

    static {
      defaultInstance = new ReqData(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.IMBalancer.b1.req.ReqData)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_IMBalancer_b1_req_ReqData_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_IMBalancer_b1_req_ReqData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rReqData.proto\022\025com.IMBalancer.b1.req\"&" +
      "\n\007ReqData\022\014\n\004data\030\001 \002(\t\022\r\n\005data2\030\002 \002(\tB\034" +
      "\n\025com.IMBalancer.b1.reqB\003Req"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_IMBalancer_b1_req_ReqData_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_IMBalancer_b1_req_ReqData_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_IMBalancer_b1_req_ReqData_descriptor,
              new java.lang.String[] { "Data", "Data2", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
