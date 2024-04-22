package urlRn;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.39.0)",
    comments = "Source: UrlRn.proto")
public final class UrlRnGrpc {

  private UrlRnGrpc() {}

  public static final String SERVICE_NAME = "urlRn.UrlRn";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.UrlByUsuarioRequest,
      urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getGetUrlByUsuarioMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getUrlByUsuario",
      requestType = urlRn.UrlRnOuterClass.UrlByUsuarioRequest.class,
      responseType = urlRn.UrlRnOuterClass.UrlByUsuarioResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.UrlByUsuarioRequest,
      urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getGetUrlByUsuarioMethod() {
    io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.UrlByUsuarioRequest, urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getGetUrlByUsuarioMethod;
    if ((getGetUrlByUsuarioMethod = UrlRnGrpc.getGetUrlByUsuarioMethod) == null) {
      synchronized (UrlRnGrpc.class) {
        if ((getGetUrlByUsuarioMethod = UrlRnGrpc.getGetUrlByUsuarioMethod) == null) {
          UrlRnGrpc.getGetUrlByUsuarioMethod = getGetUrlByUsuarioMethod =
              io.grpc.MethodDescriptor.<urlRn.UrlRnOuterClass.UrlByUsuarioRequest, urlRn.UrlRnOuterClass.UrlByUsuarioResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getUrlByUsuario"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  urlRn.UrlRnOuterClass.UrlByUsuarioRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  urlRn.UrlRnOuterClass.UrlByUsuarioResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UrlRnMethodDescriptorSupplier("getUrlByUsuario"))
              .build();
        }
      }
    }
    return getGetUrlByUsuarioMethod;
  }

  private static volatile io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.crearUrlRequest,
      urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getCrearUrlByUsuarioMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "crearUrlByUsuario",
      requestType = urlRn.UrlRnOuterClass.crearUrlRequest.class,
      responseType = urlRn.UrlRnOuterClass.UrlByUsuarioResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.crearUrlRequest,
      urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getCrearUrlByUsuarioMethod() {
    io.grpc.MethodDescriptor<urlRn.UrlRnOuterClass.crearUrlRequest, urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getCrearUrlByUsuarioMethod;
    if ((getCrearUrlByUsuarioMethod = UrlRnGrpc.getCrearUrlByUsuarioMethod) == null) {
      synchronized (UrlRnGrpc.class) {
        if ((getCrearUrlByUsuarioMethod = UrlRnGrpc.getCrearUrlByUsuarioMethod) == null) {
          UrlRnGrpc.getCrearUrlByUsuarioMethod = getCrearUrlByUsuarioMethod =
              io.grpc.MethodDescriptor.<urlRn.UrlRnOuterClass.crearUrlRequest, urlRn.UrlRnOuterClass.UrlByUsuarioResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "crearUrlByUsuario"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  urlRn.UrlRnOuterClass.crearUrlRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  urlRn.UrlRnOuterClass.UrlByUsuarioResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UrlRnMethodDescriptorSupplier("crearUrlByUsuario"))
              .build();
        }
      }
    }
    return getCrearUrlByUsuarioMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UrlRnStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlRnStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlRnStub>() {
        @java.lang.Override
        public UrlRnStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlRnStub(channel, callOptions);
        }
      };
    return UrlRnStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UrlRnBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlRnBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlRnBlockingStub>() {
        @java.lang.Override
        public UrlRnBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlRnBlockingStub(channel, callOptions);
        }
      };
    return UrlRnBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UrlRnFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UrlRnFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UrlRnFutureStub>() {
        @java.lang.Override
        public UrlRnFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UrlRnFutureStub(channel, callOptions);
        }
      };
    return UrlRnFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class UrlRnImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUrlByUsuario(urlRn.UrlRnOuterClass.UrlByUsuarioRequest request,
        io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUrlByUsuarioMethod(), responseObserver);
    }

    /**
     */
    public void crearUrlByUsuario(urlRn.UrlRnOuterClass.crearUrlRequest request,
        io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCrearUrlByUsuarioMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUrlByUsuarioMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                urlRn.UrlRnOuterClass.UrlByUsuarioRequest,
                urlRn.UrlRnOuterClass.UrlByUsuarioResponse>(
                  this, METHODID_GET_URL_BY_USUARIO)))
          .addMethod(
            getCrearUrlByUsuarioMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                urlRn.UrlRnOuterClass.crearUrlRequest,
                urlRn.UrlRnOuterClass.UrlByUsuarioResponse>(
                  this, METHODID_CREAR_URL_BY_USUARIO)))
          .build();
    }
  }

  /**
   */
  public static final class UrlRnStub extends io.grpc.stub.AbstractAsyncStub<UrlRnStub> {
    private UrlRnStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlRnStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlRnStub(channel, callOptions);
    }

    /**
     */
    public void getUrlByUsuario(urlRn.UrlRnOuterClass.UrlByUsuarioRequest request,
        io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUrlByUsuarioMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void crearUrlByUsuario(urlRn.UrlRnOuterClass.crearUrlRequest request,
        io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCrearUrlByUsuarioMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UrlRnBlockingStub extends io.grpc.stub.AbstractBlockingStub<UrlRnBlockingStub> {
    private UrlRnBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlRnBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlRnBlockingStub(channel, callOptions);
    }

    /**
     */
    public urlRn.UrlRnOuterClass.UrlByUsuarioResponse getUrlByUsuario(urlRn.UrlRnOuterClass.UrlByUsuarioRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUrlByUsuarioMethod(), getCallOptions(), request);
    }

    /**
     */
    public urlRn.UrlRnOuterClass.UrlByUsuarioResponse crearUrlByUsuario(urlRn.UrlRnOuterClass.crearUrlRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCrearUrlByUsuarioMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UrlRnFutureStub extends io.grpc.stub.AbstractFutureStub<UrlRnFutureStub> {
    private UrlRnFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UrlRnFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UrlRnFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> getUrlByUsuario(
        urlRn.UrlRnOuterClass.UrlByUsuarioRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUrlByUsuarioMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<urlRn.UrlRnOuterClass.UrlByUsuarioResponse> crearUrlByUsuario(
        urlRn.UrlRnOuterClass.crearUrlRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCrearUrlByUsuarioMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_URL_BY_USUARIO = 0;
  private static final int METHODID_CREAR_URL_BY_USUARIO = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UrlRnImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UrlRnImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_URL_BY_USUARIO:
          serviceImpl.getUrlByUsuario((urlRn.UrlRnOuterClass.UrlByUsuarioRequest) request,
              (io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse>) responseObserver);
          break;
        case METHODID_CREAR_URL_BY_USUARIO:
          serviceImpl.crearUrlByUsuario((urlRn.UrlRnOuterClass.crearUrlRequest) request,
              (io.grpc.stub.StreamObserver<urlRn.UrlRnOuterClass.UrlByUsuarioResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UrlRnBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UrlRnBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return urlRn.UrlRnOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UrlRn");
    }
  }

  private static final class UrlRnFileDescriptorSupplier
      extends UrlRnBaseDescriptorSupplier {
    UrlRnFileDescriptorSupplier() {}
  }

  private static final class UrlRnMethodDescriptorSupplier
      extends UrlRnBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UrlRnMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UrlRnGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UrlRnFileDescriptorSupplier())
              .addMethod(getGetUrlByUsuarioMethod())
              .addMethod(getCrearUrlByUsuarioMethod())
              .build();
        }
      }
    }
    return result;
  }
}
