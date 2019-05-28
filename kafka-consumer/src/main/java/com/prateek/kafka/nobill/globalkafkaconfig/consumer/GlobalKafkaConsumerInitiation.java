package com.prateek.kafka.nobill.globalkafkaconfig.consumer;

import com.prateek.common.kafka.consumer.KafkaListenerContainerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Service;

/**
 * Because by default we already set {@link KafkaListenerContainerProperties#autoStartup} is false,
 * so we have to use this class to start up Consumers manually.
 * <p>
 * If you set all KafkaConsumer.autoStart is true, then you won't need this class anymore.
 */
@Service
public class GlobalKafkaConsumerInitiation {
    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    /**
     * We cannot use @PostConstruct here because sometimes Spring invokes this method too soon.
     * It could be invoked before the application binds to Kafka.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void startAllConsumers() {
        kafkaListenerEndpointRegistry.start();
    }
}
