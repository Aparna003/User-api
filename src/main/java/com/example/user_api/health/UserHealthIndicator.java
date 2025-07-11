package com.example.user_api.health;

import com.example.user_api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserHealthIndicator implements HealthIndicator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Health health(){
        try{
            long count = userRepository.count();
            return Health.up().withDetail("userCount",count).build();
        } catch(Exception e){
            return Health.down().withDetail("error",e.getMessage()).build();
        }
    }
}
