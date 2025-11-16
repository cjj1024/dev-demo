package org.example.controller;

import org.example.service.ChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LangChain4jMemoryController {
    @Autowired
    @Qualifier("memoryChatAssistant")
    private ChatAssistant memoryChatAssistant;

    @GetMapping(value = "/langchain/memory/hello")
    public String hello(@RequestParam(name = "id") Long id,
                                       @RequestParam(name = "question", defaultValue = "你是谁") String question) {
        return memoryChatAssistant.chat(id, question);
    }

    @GetMapping(value = "/langchain/memory/write")
    public String write(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "question", defaultValue = "你是谁") String question) {
        return memoryChatAssistant.writer(id, question);
    }
}
