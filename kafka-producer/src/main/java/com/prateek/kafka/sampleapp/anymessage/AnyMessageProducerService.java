package com.prateek.kafka.sampleapp.anymessage;

import com.google.protobuf.GeneratedMessageV3;
import com.prateek.common.message.protobuf.AnyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class AnyMessageProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(AnyMessageProducerService.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    @Value("${app.topic.anyprotobuf}")
    private String topic;

    public void send(AnyMessage data) {
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}