package org.example.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.example.listener.TestChatModelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class LLMConfig {
    @Bean(name = "qwen")
    public ChatModel chatModelQwen() {
        System.out.println("api-key: " + System.getenv("qwen-api"));
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("qwen-api"))
                .modelName("qwen-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .build();
    }

    @Bean(name = "deepseek")
    public ChatModel chatModelDeepseek() {
        System.out.println("api-key: " + System.getenv("deepseek-api"));
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("deepseek-api"))
                .modelName("deepseek-chat")
                .baseUrl("https://api.deepseek.com")
                .build();
    }

    @Bean(name = "chatgpt")
    public ChatModel chatModelChatgpt() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "7897");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "7897");
        System.out.println("api-key: " + System.getenv("chatgpt-api"));
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("chatgpt-api"))
                .modelName("gpt-4.1-2025-04-14")
                .build();
    }

    @Bean(name = "local")
    public ChatModel chatModelLocal() {
        System.out.println("api-key: " + System.getenv("deepseek-api"));
        return OpenAiChatModel.builder()
                .modelName("huihui_ai/deepseek-r1-abliterated:8b")
                .baseUrl("http://localhost:11434/v1")
                .build();
    }

    @Bean(name = "poixe")
    public ChatModel chatModelPoixe() {
        System.out.println("api-key: " + System.getenv("poixe-api"));
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("poixe-api"))
                .modelName("gpt-4o-mini:free")
                .baseUrl("https://api.poixe.com/v1")
                .logRequests(true)
                .logResponses(true)
                .listeners(List.of(new TestChatModelListener()))
                .maxRetries(2)
//                .timeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean(name = "streamingChatModeLocal")
    public StreamingChatModel streamingChatModeLocal() {
        System.out.println("api-key: " + System.getenv("deepseek-api"));
        return OpenAiStreamingChatModel.builder()
                .modelName("huihui_ai/deepseek-r1-abliterated:8b")
                .baseUrl("http://localhost:11434/v1")
                .build();
    }
}
