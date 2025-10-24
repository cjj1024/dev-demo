package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JobStarter implements CommandLineRunner {
    @Autowired
    private DemoJobService demoJobService;

    @Override
    public void run(String... args) throws Exception {
        demoJobService.scheduleJobs();
        System.out.println("job start");
    }
}
