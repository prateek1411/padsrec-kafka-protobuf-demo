package com.prateek.kafka.nobill.record.consumer.usecases;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.Record;
import com.prateek.kafka.nobill.record.consumer.elk.ElkRecord;
import com.prateek.kafka.nobill.record.consumer.elk.RecordConsumerElkService;
import com.prateek.kafka.nobill.record.consumer.elk.RecordConsumerElkServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordConsumerSampleServiceImpl implements RecordConsumerSampleService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    RecordConsumerElkServiceImpl recordConsumerElkServiceImpl;

    @Override
    public void autoAck(Record record) {

    }
    @Override
    public void manualAck(Record record) {
        //logger.info("autoAck: {}", record);
        if (record.getChild().getTypeUrl().equals("type.sinchapis.com/com.prateek.common.message.protobuf.PadsRecord")) {
                PadsRecord padsRecord = null;
                try {
                    padsRecord = record.getChild().unpack(PadsRecord.class);
                    ElkRecord elkRecord = new ElkRecord(padsRecord.getSeqno(),JsonFormat.printer().print(padsRecord));
                    recordConsumerElkServiceImpl.save(elkRecord);
                    Iterable <ElkRecord> elkRecords =  new ArrayList<>();
                    elkRecords = recordConsumerElkServiceImpl.findAll();
                    for (ElkRecord elk : elkRecords)  {
                        logger.info("PadsRecord = " + elk.getRecord());
                    }

                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }

            }

        }

    @Override
    public void autoAckError() {

    }

    @Override
    public void autoAckErrorAtOffset(long offset) {

    }

    @Override
    public void manualAckError() {

    }

    @Override
    public void manualAckErrorAtOffset(long offset) {

    }

}
