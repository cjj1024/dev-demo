package org.example;


import org.jobrunr.configuration.JobRunr;
import org.jobrunr.configuration.JobRunrConfiguration;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class JobRunnerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobRunnerDemoApplication.class, args);
    }

    @Bean
    public StorageProvider storageProvider() {
        return new InMemoryStorageProvider();
    }

    @Bean
    public JobRunrConfiguration jobRunrConfig(org.jobrunr.storage.StorageProvider storageProvider) {
        return JobRunr.configure()
                .useStorageProvider(storageProvider)
                .useBackgroundJobServer()
                .useDashboard(8000);
    }
}
