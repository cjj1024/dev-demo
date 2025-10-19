package org.example;

import io.grpc.stub.StreamObserver;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;

public class GrpcHelloImpl extends org.example.grpc.GrpcHelloGrpc.GrpcHelloImplBase {
    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse helloResponse = HelloResponse.newBuilder().setMessage("grpc-provider, hello " + request.getName()).build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }
}
