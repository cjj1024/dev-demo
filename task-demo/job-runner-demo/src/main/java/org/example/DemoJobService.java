package org.example;

import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoJobService {
    @Autowired
    JobScheduler jobScheduler;

    @Job(name = "hello")
    public void printHello(String name) {
        System.out.println("Hello, " + name + "! Time: " + java.time.LocalDateTime.now());
    }

    public void scheduleJobs() {
        jobScheduler.scheduleRecurrently("hello",
                "0 2 * * *",
                () -> printHello("world"));
    }
}
