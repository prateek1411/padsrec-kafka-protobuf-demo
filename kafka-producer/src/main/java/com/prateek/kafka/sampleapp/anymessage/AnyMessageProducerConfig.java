package com.prateek.kafka.sampleapp.anymessage;

import com.google.protobuf.GeneratedMessageV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import com.prateek.common.kafka.producer.KafkaGlobalProducerProperties;
import com.prateek.common.kafka.producer.KafkaProducerTemplateFactory;

@Configuration
public class AnyMessageProducerConfig {
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link KafkaGlobalProducerProperties}
     */
    @Autowired
    private KafkaGlobalProducerProperties kafkaGlobalProducerProperties;

    @Bean
    public  <T extends GeneratedMessageV3> KafkaTemplate<String, T> kafkaTemplate() {
        KafkaProducerTemplateFactory<T> kafkaProducerTemplateFactory = new KafkaProducerTemplateFactory<>(kafkaGlobalProducerProperties);
        return kafkaProducerTemplateFactory.createProtobufKafkaTemplate();
    }
}