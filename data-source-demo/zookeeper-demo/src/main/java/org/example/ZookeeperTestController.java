package org.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zookeeper-demo")
public class ZookeeperTestController {
    private final ZkService zkService;
    public static final String prefix = "/";

    public ZookeeperTestController(ZkService zkService) {
        this.zkService = zkService;
    }

    @GetMapping("/{path}")
    public String get(@PathVariable String path) throws Exception {
        String value = zkService.readNode(prefix + path);
        return value;
    }

    @PostMapping("/{path}")
    public Object put(@PathVariable String path, @RequestParam String value) throws Exception {
        Object res = zkService.createNode(prefix + path, value);
        return res;
    }

    @DeleteMapping("/{path}")
    public Object delete(@PathVariable String path) throws Exception {
        Object res = zkService.deleteNode(prefix + path);
        return res;
    }
}
