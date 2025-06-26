package com.example.user_api;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTask {
    @Scheduled(cron = "*/10 * * * * *")
    public void reportTime() {
        System.out.println("Current time: " + LocalDateTime.now());
    }
}
