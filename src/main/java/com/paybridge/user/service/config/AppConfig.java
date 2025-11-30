package com.paybridge.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean  // Registers RestTemplate as a Spring bean (singleton by default)
    public RestTemplate restTemplate() {
        return new RestTemplate();  // Basic instance—customize if needed (e.g., timeouts)
    }
}
