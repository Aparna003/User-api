package com.example.user_api.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevEnvService implements EnvService {

    @Override
    public String getEnvironmentMessage() {
        return "Currently running in Dev environment";
    }
}
