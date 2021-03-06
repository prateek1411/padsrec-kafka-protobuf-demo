package com.prateek.kafka.nobill.record;

import com.google.protobuf.GeneratedMessageV3;
import com.prateek.common.kafka.producer.KafkaGlobalProducerProperties;
import com.prateek.common.kafka.producer.KafkaProducerTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class RecordProducerConfig {
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link KafkaGlobalProducerProperties}
     */
    @Autowired
    private KafkaGlobalProducerProperties kafkaGlobalProducerProperties;

    @Bean
    public <T extends GeneratedMessageV3> KafkaTemplate<String, T> kafkaTemplate() {
        KafkaProducerTemplateFactory<T> kafkaProducerTemplateFactory = new KafkaProducerTemplateFactory<>(kafkaGlobalProducerProperties);
        return kafkaProducerTemplateFactory.createProtobufKafkaTemplate();
    }
}