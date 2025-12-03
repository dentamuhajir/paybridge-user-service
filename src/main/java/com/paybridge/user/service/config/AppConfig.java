package com.paybridge.user.service.config;

import com.paybridge.user.service.logging.TraceRestTemplateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.getInterceptors().add(new TraceRestTemplateInterceptor());
        return rt;
    }
}
