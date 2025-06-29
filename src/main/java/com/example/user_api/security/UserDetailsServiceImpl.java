package com.example.user_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsServiceImpl {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User
                .withUsername("admin")
                .password("{noop}admin123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
