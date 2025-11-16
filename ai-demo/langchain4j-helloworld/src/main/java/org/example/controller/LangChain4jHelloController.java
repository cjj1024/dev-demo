package org.example.controller;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Base64;

@RestController
public class LangChain4jHelloController {
    @Autowired
    @Qualifier("qwen")
    private ChatModel chatModelQwen;

    @Autowired
    @Qualifier("deepseek")
    private ChatModel chatModelDeepseek;

    @Autowired
    @Qualifier("chatgpt")
    private ChatModel chatModelChatgpt;

    @Autowired
    @Qualifier("local")
    private ChatModel chatModelLocal;

    @Autowired
    @Qualifier("poixe")
    private ChatModel chatModelPoixe;

    @Autowired
    @Qualifier("streamingChatModeLocal")
    private StreamingChatModel streamingChatModeLocal;

    @Value("classpath:static/images/test.png")
    private Resource resource;

    @GetMapping("/langchain/hello")
    public String hello (@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        String response = chatModelQwen.chat(question);
        return response;
    }

    @GetMapping("/langchain/hello2")
    public String hello2 (@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        String response = chatModelDeepseek.chat(question);
        return response;
    }

    @GetMapping("/langchain/hello3")
    public String hello3 (@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        String response = chatModelChatgpt.chat(question);
        return response;
    }

    @GetMapping("/langchain/hello4")
    public String hello4 (@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        ChatResponse chatResponse = chatModelPoixe.chat(UserMessage.from(question));
        return chatResponse.aiMessage().text();
    }

    @GetMapping("/langchain/hello5")
    public String hello5 (@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        String response = chatModelLocal.chat(question);
        return response;
    }

    @GetMapping("/langchain/image")
    public String image(@RequestParam(name = "question") String question) throws IOException {
        String imageBase64 = Base64.getEncoder().encodeToString(resource.getContentAsByteArray());

        UserMessage userMessage = UserMessage.from(
                TextContent.from(question),
                ImageContent.from(imageBase64, "image/png"));
        ChatResponse chatResponse = chatModelPoixe.chat(userMessage);

        return chatResponse.aiMessage().text();
    }

    @GetMapping(value = "/langchain/streaming/hello")
    public Flux<String> streamingHello(@RequestParam(name = "question", defaultValue = "你是谁") String question) {

        return Flux.create(emitter -> {
            streamingChatModeLocal.chat(question, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    System.out.println(partialResponse);
                    emitter.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    emitter.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    emitter.error(throwable);
                }
            });
        });
    }
}
