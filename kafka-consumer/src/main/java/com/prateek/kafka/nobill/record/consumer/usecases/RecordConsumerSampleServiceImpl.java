package com.prateek.kafka.nobill.record.consumer.usecases;

import com.google.protobuf.InvalidProtocolBufferException;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class RecordConsumerSampleServiceImpl implements RecordConsumerSampleService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void autoAck(Record record) {

        logger.info("autoAck: {}", record);
        if (record.getClass().getCanonicalName().equals(Record.class.getCanonicalName())) {
            Record anyMessage = (Record) record;
            if (anyMessage.getChild().is(PadsRecord.class)) {
                PadsRecord padsRecord = null;
                try {
                    padsRecord = anyMessage.getChild().unpack(PadsRecord.class);
                    logger.info("PadsRecord = " + padsRecord.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void manualAck(Record record) {

        logger.info("manualAck: {}", record);

        if (record.getClass().getCanonicalName().equals(Record.class.getCanonicalName())) {
            Record anyMessage = (Record) record;
            if (anyMessage.getChild().is(PadsRecord.class)) {
                PadsRecord padsRecord = PadsRecord.newBuilder().build();
                try {
                    padsRecord = anyMessage.getChild().unpack(PadsRecord.class);
                    logger.info("PadsRecord = " + padsRecord.toString());
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            }

        }
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
