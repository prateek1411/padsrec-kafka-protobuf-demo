package com.prateek.kafka.sampleapp.anymessage.consumer.listener;

import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.kafka.serialization.protobuf.DeserializedRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.prateek.kafka.sampleapp.anymessage.consumer.usecases.AnyMessageConsumerSampleService;

import java.lang.invoke.MethodHandles;

/**
 * For some reason, the manual acknowledge doesn't work???
 * https://stackoverflow.com/questions/41497790/cannot-disable-manual-commits-on-kafka-message-using-spring-integration-kafka-in
 */
@Service
public class AnyMessageManualAckListener {

    private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private AnyMessageConsumerSampleService anyMessageConsumerSampleService;

    //Note: this groupId is different from AnyMessageAutoAckListener
    @KafkaListener(id = "anyMessageManualAckListener", groupId = "anyMessageManualAckGroup", topics = "${app.topic.anyprotobuf}",
            containerFactory = "anyMessageManualAckListenerContainerFactory",
            errorHandler = "anyMessageManualAckListenerErrorHandler")
    public void receive(@Payload DeserializedRecord<AnyMessage> message, @Headers MessageHeaders headers, Acknowledgment acknowledgment) {
        AnyMessage data = message.getData();
        logReceiveData(data, headers);
        if (StringUtils.isEmpty(data.getPadsrecord())) {
            //We do this to test the Error Handler
            throw new IllegalArgumentException("The real name must be not empty: " + data);
        } else {
            anyMessageConsumerSampleService.manualAck(data);
        }
        // Note: Even if the don't call acknowledge(), the Listener still continue processing the next item. It doesn't stuck here.
        // However, when we restart the application, it will replay old records which are not acknowledged yet.
        acknowledgment.acknowledge();
    }

    private void logReceiveData(AnyMessage data, MessageHeaders headers) {
        Long offset = (Long)headers.get(KafkaHeaders.OFFSET);
        LOG.info("[MANUAL-ACK]received record[{}]='{}'",offset, data);
    }
}