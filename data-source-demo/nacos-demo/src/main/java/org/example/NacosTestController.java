package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.Enumeration;

@RefreshScope
@RestController
@RequestMapping("/nacos-demo")
public class NacosTestController {
    @Value("${name}")
    private String serviceName;
    @Autowired
    private Environment env;

    @GetMapping("/hello")
    public String hello() {
        return serviceName;
    }

    @GetMapping("/hello2")
    public String hello2() {
        return env.getProperty("name");
    }
}
