package com.prateek.kafka.nobill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class ConsumerApplication {

    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
