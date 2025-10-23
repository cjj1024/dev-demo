package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class KafkaDemoApplication {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new int[]{}));
        ConfigurableApplicationContext applicationContext = SpringApplication.run(KafkaDemoApplication.class, args);
    }
}
