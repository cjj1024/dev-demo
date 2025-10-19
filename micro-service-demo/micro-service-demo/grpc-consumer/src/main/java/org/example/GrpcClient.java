package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.GrpcHelloGrpc;
import org.example.grpc.HelloResponse;
import org.example.grpc.HelloRequest;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50058)
                .usePlaintext()
                .build();

        GrpcHelloGrpc.GrpcHelloBlockingStub stub =  GrpcHelloGrpc.newBlockingStub(channel);


        HelloResponse reply = stub.hello(
                HelloRequest.newBuilder().setName("grpc").build()
        );

        System.out.println("Server says: " + reply.getMessage());
        channel.shutdown();
    }
}
