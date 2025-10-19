package org.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: hello.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GrpcHelloGrpc {

  private GrpcHelloGrpc() {}

  public static final java.lang.String SERVICE_NAME = "org.example.grpc.GrpcHello";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.grpc.HelloRequest,
      org.example.grpc.HelloResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = org.example.grpc.HelloRequest.class,
      responseType = org.example.grpc.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.grpc.HelloRequest,
      org.example.grpc.HelloResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.HelloRequest, org.example.grpc.HelloResponse> getHelloMethod;
    if ((getHelloMethod = GrpcHelloGrpc.getHelloMethod) == null) {
      synchronized (GrpcHelloGrpc.class) {
        if ((getHelloMethod = GrpcHelloGrpc.getHelloMethod) == null) {
          GrpcHelloGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<org.example.grpc.HelloRequest, org.example.grpc.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new GrpcHelloMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GrpcHelloStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcHelloStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcHelloStub>() {
        @java.lang.Override
        public GrpcHelloStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcHelloStub(channel, callOptions);
        }
      };
    return GrpcHelloStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GrpcHelloBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcHelloBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcHelloBlockingStub>() {
        @java.lang.Override
        public GrpcHelloBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcHelloBlockingStub(channel, callOptions);
        }
      };
    return GrpcHelloBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GrpcHelloFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GrpcHelloFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GrpcHelloFutureStub>() {
        @java.lang.Override
        public GrpcHelloFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GrpcHelloFutureStub(channel, callOptions);
        }
      };
    return GrpcHelloFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void hello(org.example.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.HelloResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service GrpcHello.
   */
  public static abstract class GrpcHelloImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return GrpcHelloGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service GrpcHello.
   */
  public static final class GrpcHelloStub
      extends io.grpc.stub.AbstractAsyncStub<GrpcHelloStub> {
    private GrpcHelloStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcHelloStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcHelloStub(channel, callOptions);
    }

    /**
     */
    public void hello(org.example.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.HelloResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service GrpcHello.
   */
  public static final class GrpcHelloBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<GrpcHelloBlockingStub> {
    private GrpcHelloBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcHelloBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcHelloBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.example.grpc.HelloResponse hello(org.example.grpc.HelloRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service GrpcHello.
   */
  public static final class GrpcHelloFutureStub
      extends io.grpc.stub.AbstractFutureStub<GrpcHelloFutureStub> {
    private GrpcHelloFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GrpcHelloFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GrpcHelloFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.grpc.HelloResponse> hello(
        org.example.grpc.HelloRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((org.example.grpc.HelloRequest) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.HelloResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getHelloMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.example.grpc.HelloRequest,
              org.example.grpc.HelloResponse>(
                service, METHODID_HELLO)))
        .build();
  }

  private static abstract class GrpcHelloBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GrpcHelloBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.grpc.Hello.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("GrpcHello");
    }
  }

  private static final class GrpcHelloFileDescriptorSupplier
      extends GrpcHelloBaseDescriptorSupplier {
    GrpcHelloFileDescriptorSupplier() {}
  }

  private static final class GrpcHelloMethodDescriptorSupplier
      extends GrpcHelloBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    GrpcHelloMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (GrpcHelloGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GrpcHelloFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .build();
        }
      }
    }
    return result;
  }
}
