package com.prateek.kafka.sampleapp.anymessage.consumer.usecases;

import com.prateek.common.message.protobuf.AnyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class AnyMessageConsumerSampleServiceImpl implements AnyMessageConsumerSampleService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void autoAck(AnyMessage anyMessage) {
        logger.info("autoAck: {}", anyMessage);
    }

    @Override
    public void manualAck(AnyMessage anyMessage) {
        logger.info("manualAck: {}", anyMessage);
    }

    @Override
    public void autoAckError() {
        logger.info("autoAckError");
    }

    @Override
    public void autoAckErrorAtOffset(long offset) {
        logger.info("autoAckErrorAtOffset: {}", offset);
    }

    @Override
    public void manualAckError() {
        logger.info("manualAckError");
    }

    @Override
    public void manualAckErrorAtOffset(long offset) {
        logger.info("manualAckErrorAtOffset: {}", offset);
    }
}
