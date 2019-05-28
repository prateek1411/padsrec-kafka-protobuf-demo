package com.prateek.kafka.nobill.record;

import com.google.protobuf.GeneratedMessageV3;
import com.prateek.common.message.protobuf.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class RecordProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(RecordProducerService.class);

    @Autowired
    private KafkaTemplate<String, GeneratedMessageV3> kafkaTemplate;

    @Value("${app.topic.record}")
    private String topic;

    public void send(Record data) {
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        kafkaTemplate.send(topic, data);
    }
}