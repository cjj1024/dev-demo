package org.example.controller;

import org.example.service.ChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LangChain4jMcpController {
    @Autowired
    ChatAssistant chatAssistant;

    @PostMapping("/langchain/mcp/chat")
    public String chat(@RequestParam(name = "question") String question) {
        String response = chatAssistant.chat(0L, question);

        return response;
    }
}
