package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50058)
                .addService(new GrpcHelloImpl())
                .build()
                .start();
        System.out.println("gRPC server started on port 50058");
        server.awaitTermination();
    }
}
