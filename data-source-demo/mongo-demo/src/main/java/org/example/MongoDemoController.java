package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mongo-demo")
public class MongoDemoController {
    @Autowired
    private UserRepository repo;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @GetMapping("/search")
    public List<User> findByName(@RequestParam String name) {
        return repo.findByName(name);
    }
}
