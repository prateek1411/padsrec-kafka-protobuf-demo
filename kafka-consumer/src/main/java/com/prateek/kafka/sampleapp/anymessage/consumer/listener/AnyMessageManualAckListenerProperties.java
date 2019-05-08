package com.prateek.kafka.sampleapp.anymessage.consumer.listener;

import com.prateek.common.kafka.consumer.KafkaListenerContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "anymessage-manual-commit-consumer")
public class AnyMessageManualAckListenerProperties extends KafkaListenerContainerProperties {
    //Just extend the superclass so that we can apply the @ConfigurationProperties on the properties of the super class.
    //No need to override anything here.
    public AnyMessageManualAckListenerProperties(){

    }
}