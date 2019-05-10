package com.prateek.kafka.sampleapp.anymessage.consumer.listener;

import com.google.protobuf.InvalidProtocolBufferException;
import com.prateek.common.kafka.serialization.protobuf.DeserializedRecord;
import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.kafka.sampleapp.anymessage.consumer.usecases.AnyMessageConsumerSampleService;
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
public class AnyMessageAutoAckListener {

    private static final Logger LOG = LoggerFactory.getLogger(AnyMessageAutoAckListener.class);

    @Autowired
    private AnyMessageConsumerSampleService anyMessageConsumerSampleService;

    @KafkaListener(id = "anyMessageAutoAckListener", groupId = "anyMessageAutoAckGroup", topics = "${app.topic.anyprotobuf}",
            containerFactory = "anyMessageAutoAckListenerContainerFactory", errorHandler = "anyMessageErrorHandler")
    public void receive(@Payload DeserializedRecord<AnyMessage> message, @Headers MessageHeaders headers) {
        AnyMessage data = message.getData();
        logReceiveData(data, headers);
        if (StringUtils.isEmpty(data.getPadsrecord())) {
            //We do this to test the Error Handler
            throw new IllegalArgumentException("The real name must be not empty: " + data);
        } else {
            anyMessageConsumerSampleService.autoAck(data);
        }
    }

    private void logReceiveData(AnyMessage data, MessageHeaders headers) {
        Long offset = (Long) headers.get(KafkaHeaders.OFFSET);

        if (data.getClass().getCanonicalName().equals(AnyMessage.class.getCanonicalName())) {
            AnyMessage anyMessage = (AnyMessage) data;
            if (anyMessage.getPadsrecord().is(PadsRecord.class)) {
                PadsRecord padsRecord = null;
                try {
                    padsRecord = anyMessage.getPadsrecord().unpack(PadsRecord.class);
                    LOG.info("PadsRecord = " + padsRecord.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            }

        }
        LOG.info("[AUTO-ACK]received record[{}]='{}'", offset, data);
    }
}