package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka-demo")
public class KafkaDemoController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    public void send(@RequestBody TopicDemoMessage msg) {
        kafkaProducerService.send(msg.topic, msg.value);
    }

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopicDemoMessage {
        String topic;
        String value;
    }
}
