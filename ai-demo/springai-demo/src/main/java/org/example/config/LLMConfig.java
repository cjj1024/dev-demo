package org.example.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LLMConfig {
    @Bean("poixe")
    public ChatModel chatModelPoixe() {
        var openAiApi = OpenAiApi.builder()
                .apiKey(System.getenv("poixe-api"))
                .baseUrl("https://api.poixe.com")
                .build();
        var openAiChatOptions = OpenAiChatOptions.builder()
                .model("gpt-4o-mini:free")
                .temperature(0.4)
                .maxTokens(200)
                .build();
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(openAiChatOptions)
                .build();
    }

    @Bean("local")
    public ChatModel chatModelLocal() {
        var openAiApi = OpenAiApi.builder()
                .apiKey("none")
                .baseUrl("http://localhost:11434")
                .build();
        var openAiChatOptions = OpenAiChatOptions.builder()
                .model("huihui_ai/deepseek-r1-abliterated:8b")
                .build();
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(openAiChatOptions)
                .build();
    }

    @Bean
    public ChatClient chatClient(@Qualifier("local") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是一个黑长直黑丝JK")
                .build();
    }
}
