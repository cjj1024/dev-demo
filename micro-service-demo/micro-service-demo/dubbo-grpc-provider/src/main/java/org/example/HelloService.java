package org.example;


import org.apache.dubbo.config.annotation.DubboService;
import org.example.dubbo.grpc.DubboHelloTriple;
import org.example.dubbo.grpc.HelloRequest;
import org.example.dubbo.grpc.HelloResponse;

@DubboService
public class HelloService extends DubboHelloTriple.HelloImplBase {
    @Override
    public HelloResponse hello(HelloRequest request) {
        return HelloResponse.newBuilder().setMessage("dubbo-grpc-provider, hello " + request.getName()).build();
    }
}
