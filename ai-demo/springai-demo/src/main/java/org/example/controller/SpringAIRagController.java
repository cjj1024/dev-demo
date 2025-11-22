package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class SpringAIRagController {
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

    @PostMapping("/springai/rag/add")
    public void add(@RequestParam(name = "prompt") String prompt) {
        vectorStore.add(List.of(new Document(prompt)));
    }

    @GetMapping("/springai/rag/hello")
    public String hello(@RequestParam(name = "prompt") String prompt) {
        System.out.println(prompt);
        return chatClient.prompt()
                .user(prompt)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }
}
