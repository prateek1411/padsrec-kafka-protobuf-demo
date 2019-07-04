package com.prateek.kafka.nobill.record.consumer;

import com.prateek.common.kafka.consumer.KafkaListenerContainerFactoryConstructor;
import com.prateek.common.kafka.serialization.protobuf.ProtobufDeserializer;
import com.sinch.common.message.protobuf.Record;
import com.prateek.kafka.nobill.record.consumer.listener.RecordAutoAckListenerProperties;
import com.prateek.kafka.nobill.record.consumer.listener.RecordManualAckListenerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
@EnableKafka //@EnableKafka is used to enable detection of @KafkaListener annotation.
public class RecordConsumerConfig {
    //AUTO ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    /**
     * All the properties values of this bean was loaded from *.yml file.
     * It was configured in {@link RecordAutoAckListenerProperties}
     */
    @Autowired
    private RecordAutoAckListenerProperties recordAutoAckListenerProperties;
    //MANUAL ACKNOWLEDGE LISTENER /////////////////////////////////////////////////////////////////////////////
    @Autowired
    private RecordManualAckListenerProperties recordManualAckListenerProperties;

    /**
     * For each type of message (e.g. AnyMessage), we have to create a separated ListenerContainerFactory bean.
     * It's not so good, but I cannot find any other convenient ways to do it right now.
     * <br/>
     * The root cause is the Protobuf parser instance is coupled to the generated message type.
     * Please view more in {@link ProtobufDeserializer}
     *
     * @return
     */
    @Bean("recordAutoAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Record> recordAutoAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(recordAutoAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Record.class);
    }

    @Bean("recordManualAckListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Record> recordManualAckListenerContainerFactory() {
        KafkaListenerContainerFactoryConstructor kafkaListenerContainerFactoryConstructor = new KafkaListenerContainerFactoryConstructor(recordManualAckListenerProperties);
        return kafkaListenerContainerFactoryConstructor.createProtobufConcurrentConsumerContainerFactory(Record.class);
    }
}