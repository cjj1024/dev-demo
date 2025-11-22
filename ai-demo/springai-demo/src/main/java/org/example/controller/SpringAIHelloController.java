package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SpringAIHelloController {
    @Autowired
    @Qualifier("poixe")
    private ChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/springai/hello")
    public String hello(@RequestParam(name = "prompt") String prompt) {
        return chatModel.call(prompt);
    }

    @GetMapping("/springai/hello2")
    public String hello2(@RequestParam(name = "prompt") String prompt) {
        return chatClient.prompt(prompt)
                .call()
                .content();
    }
    @GetMapping("/springai/hello3")
    public Flux<String> hello3(@RequestParam(name = "prompt") String prompt) {
        return chatClient.prompt(prompt)
                .stream()
                .content();
    }
}
