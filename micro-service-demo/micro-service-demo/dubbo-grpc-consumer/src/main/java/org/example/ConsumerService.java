package org.example;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.dubbo.grpc.Hello;
import org.example.dubbo.grpc.HelloRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer-grpc")
public class ConsumerService {
    @DubboReference
    private Hello hello;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return hello.hello(HelloRequest.newBuilder().setName(name).build()).getMessage();
    }
}
