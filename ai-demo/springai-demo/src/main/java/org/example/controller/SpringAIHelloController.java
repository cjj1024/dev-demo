package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Base64;

@RestController
public class SpringAIHelloController {
    @Autowired
    @Qualifier("poixe")
    private ChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    @Qualifier("localImage")
    private ImageModel imageModel;

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

    @GetMapping("/springai/image")
    public void image(@RequestParam(name = "prompt") String prompt,
                      HttpServletResponse response) throws IOException {
        ImageResponse imageResponse = imageModel.call(new ImagePrompt(prompt));

        String imageUrl = imageResponse.getResult().getOutput().getUrl();

        URL url = URI.create(imageUrl).toURL();
        InputStream inputStream = url.openStream();

        response.setHeader("Content-Type", MediaType.IMAGE_PNG_VALUE);
        response.getOutputStream().write(inputStream.readAllBytes());
        response.getOutputStream().flush();
    }

}
