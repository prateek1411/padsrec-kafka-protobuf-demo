package com.prateek.kafka.nobill.record.consumer.listener;

import com.prateek.common.kafka.consumer.KafkaListenerContainerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "record-manual-commit-consumer")
public class RecordManualAckListenerProperties extends KafkaListenerContainerProperties {
    //Just extend the superclass so that we can apply the @ConfigurationProperties on the properties of the super class.
    //No need to override anything here.
    public RecordManualAckListenerProperties() {

    }
}