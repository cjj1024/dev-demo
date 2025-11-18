package org.example.config;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.example.listener.TestChatModelListener;
import org.example.service.ChatAssistant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

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
                .maxRetries(1)
                .timeout(Duration.ofHours(1))
                .logRequests(true)
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

    @Bean(name = "localEmbeddingModel")
    public EmbeddingModel localEmbeddingModel() {
        return OpenAiEmbeddingModel.builder()
                .modelName("embeddinggemma:300m")
                .baseUrl("http://localhost:11434/v1")
                .timeout(Duration.ofMinutes(10))
                .build();
    }

    @Bean(name = "memoryChatAssistant")
    public ChatAssistant memoryChatAssistant(@Qualifier("local") ChatModel chatModel,
                                             RedisChatMemoryStore redisChatMemoryStore,
                                             EmbeddingStore<TextSegment> embeddingStore,
                                             EmbeddingModel embeddingModel) {
        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)
//                .chatMemoryStore(redisChatMemoryStore)
                .build();

        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("invoice_assistant")
                .description("开发票")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("name", "抬头")
                        .addIntegerProperty("amount", "金额")
                        .build())
                .build();
        ToolExecutor toolExecutor = ((toolExecutionRequest, memoryId) -> {
            System.out.println(toolExecutionRequest.id());
            System.out.println(toolExecutionRequest.name());
            System.out.println(toolExecutionRequest.arguments());
            return "开具成功";
        });


        EmbeddingStoreContentRetriever embeddingStoreContentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        return AiServices.builder(ChatAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(chatMemoryProvider)
//                .tools(Map.of(toolSpecification, toolExecutor))
//                .contentRetriever(embeddingStoreContentRetriever)
                .build();
    }
}
