package com.alzamer.ahmad.springboot_rest_mcp.Config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    //might use it later to build a custom tool in java
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}