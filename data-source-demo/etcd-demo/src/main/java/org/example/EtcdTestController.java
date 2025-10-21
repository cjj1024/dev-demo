package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/etcd")
public class EtcdTestController {
    private final EtcdConfigLoader loader;

    public EtcdTestController(EtcdConfigLoader loader) {
        this.loader = loader;
    }

    @GetMapping("/{key}")
    public String get(@PathVariable String key) throws Exception {
        return loader.getConfig(key);
    }
}
