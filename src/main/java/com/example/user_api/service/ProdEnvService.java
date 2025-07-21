package com.example.user_api.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ProdEnvService implements EnvService{
    @Override
    public String getEnvironmentMessage() {
        return "Running in production environment";
    }
}
