package com.prateek.kafka.sampleapp.anymessage.consumer;

import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.kafka.sampleapp.anymessage.consumer.listener.AnyMessageManualAckListenerProperties;
import com.prateek.common.kafka.consumer.KafkaListenerContainerFactoryConstructor;
import com.prateek.common.kafka.serialization.protobuf.ProtobufDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import com.prateek.kafka.sampleapp.anymessage.consumer.listener.AnyMessageAutoAckListenerProperties;

@Configuration
@EnableKafka //@EnableKafka is used to enable detection of @KafkaListener annotation.
public class AnyMessageConsumerConfig {
    //AUTO ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link AnyMessageAutoAckListenerProperties}
     */
    @Autowired
    private AnyMessageAutoAckListenerProperties anyMessageAutoAckListenerProperties;

    /**
     * For each type of message (e.g. AnyMessage), we have to create a separated ListenerContainerFactory bean.
     * It's not so good, but I cannot find any other convenient ways to do it right now.
     * <br/>
     * The root cause is the Protobuf parser instance is coupled to the generated message type.
     * Please view more in {@link ProtobufDeserializer}
     * @return
     */
    @Bean("anyMessageAutoAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AnyMessage> anyMessageAutoAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(anyMessageAutoAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(AnyMessage.class);
    }

    //MANUAL ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    @Autowired
    private AnyMessageManualAckListenerProperties anyMessageManualAckListenerProperties;

    @Bean("anyMessageManualAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AnyMessage> anyMessageManualAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(anyMessageManualAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(AnyMessage.class);
    }
}