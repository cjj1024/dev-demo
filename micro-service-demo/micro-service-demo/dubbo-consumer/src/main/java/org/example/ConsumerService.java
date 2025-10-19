package org.example;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class ConsumerService {
    @DubboReference
    private DemoService myService;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam String name) {
        return myService.sayHello(name);
    }
}
