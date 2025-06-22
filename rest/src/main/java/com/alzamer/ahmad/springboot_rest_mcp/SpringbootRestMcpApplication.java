package com.alzamer.ahmad.springboot_rest_mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringbootRestMcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestMcpApplication.class, args);
    }

}
