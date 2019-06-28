package com.prateek.kafka.nobill.record.consumer.usecases;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.Record;
import com.prateek.kafka.nobill.record.consumer.elk.ElkConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

@Service
public class RecordConsumerSampleServiceImpl implements RecordConsumerSampleService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //  @Autowired
    //  RecordConsumerElkServiceImpl recordConsumerElkServiceImpl;

    @Autowired
    ElkConfig elkConfig;

    @Override
    public void autoAck(Record record) {

    }

    @Override
    public void manualAck(Record record) {
        if (record.getChild().is(PadsRecord.class)) {
            try {

                IndexRequest request = new IndexRequest("record_index")
                        .id(Long.toString(record.getSeqno()))
                        .source(JsonFormat.printer().print(record.getChild().unpack(PadsRecord.class)), XContentType.JSON);

                IndexResponse indexResponse = elkConfig.client().index(request, RequestOptions.DEFAULT);

                logger.info("ELK HTTP RESP: " + indexResponse.status().getStatus());

            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
