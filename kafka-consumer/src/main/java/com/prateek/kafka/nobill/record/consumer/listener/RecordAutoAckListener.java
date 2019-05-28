package com.prateek.kafka.nobill.record.consumer.listener;

import com.prateek.common.kafka.serialization.protobuf.DeserializedRecord;
import com.prateek.common.message.protobuf.Record;
import com.prateek.kafka.nobill.record.consumer.usecases.RecordConsumerSampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RecordAutoAckListener {

    private static final Logger LOG = LoggerFactory.getLogger(RecordAutoAckListener.class);

    @Autowired
    private RecordConsumerSampleService recordConsumerSampleService;

    @KafkaListener(id = "recordAutoAckListener", groupId = "recordAutoAckGroup", topics = "${app.topic.record}",
            containerFactory = "recordAutoAckListenerContainerFactory", errorHandler = "recordErrorHandler")
    public void receive(@Payload DeserializedRecord<Record> message, @Headers MessageHeaders headers) {
        Record data = message.getData();
        logReceiveData(data, headers);
        if (StringUtils.isEmpty(data.getChild())) {
            //We do this to test the Error Handler
            throw new IllegalArgumentException("The real name must be not empty: " + data);
        } else {
            recordConsumerSampleService.autoAck(data);
        }
    }

    private void logReceiveData(Record data, MessageHeaders headers) {
        Long offset = (Long) headers.get(KafkaHeaders.OFFSET);
        LOG.info("[AUTO-ACK]received record[{}]='{}'", offset, data);
    }
}