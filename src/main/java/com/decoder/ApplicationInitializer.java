package com.decoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.decoder.controller", "com.decoder.service", "com.decoder.repository"})
public class ApplicationInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationInitializer.class, args);
    }
}
