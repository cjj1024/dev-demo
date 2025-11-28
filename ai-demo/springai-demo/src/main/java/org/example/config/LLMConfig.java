package org.example.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.openai.*;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

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

    @Bean("localImage")
    public ImageModel imageModelLocal() {
        OpenAiImageApi openAiImageApi = OpenAiImageApi.builder()
                .apiKey("none")
                .baseUrl("http://localhost:8080")
                .build();
        OpenAiImageOptions openAiImageOptions = OpenAiImageOptions.builder()
                .model("sd-1.5-ggml")
                .quality("standard")
                .N(1)
                .height(128)
                .width(128)
                .build();
        RetryTemplate retryTemplate = RetryTemplate.builder()
                .withTimeout(Duration.ofMinutes(10))
                .build();
        OpenAiImageModel openAiImageModel = new OpenAiImageModel(openAiImageApi, openAiImageOptions, retryTemplate);
        return openAiImageModel;

    }

    @Bean
    public EmbeddingModel embeddingModel() {
        var openAiApi = OpenAiApi.builder()
                .apiKey("none")
                .baseUrl("http://localhost:11434")
                .build();
        OpenAiEmbeddingOptions openAiEmbeddingOptions = OpenAiEmbeddingOptions.builder()
                .model("embeddinggemma:300m")
                .build();
        return new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED, openAiEmbeddingOptions);
    }


    @Bean
    public ChatClient chatClient(@Qualifier("local") ChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是一个黑长直黑丝JK")
                .build();
    }
}
