package com.prateek.loadgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class LoadGeneratorApplication {
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        SpringApplication.run(LoadGeneratorApplication.class, args);
    }
}
