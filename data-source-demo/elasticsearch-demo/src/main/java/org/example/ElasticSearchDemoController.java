package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elasticsearch-demo")
public class ElasticSearchDemoController {
    @Autowired
    UserRepository repo;

    @PostMapping
    public User create(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping("/{name}")
    public List<User> get(@PathVariable String name) {
        return repo.findByName(name);
    }
}
