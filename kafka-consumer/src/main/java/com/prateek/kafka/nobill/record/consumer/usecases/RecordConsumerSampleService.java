package com.prateek.kafka.nobill.record.consumer.usecases;


import com.sinch.common.message.protobuf.Record;

/**
 * In this consumer package, there's no implementation class for this interface because it will be mocked.
 */
public interface RecordConsumerSampleService {
    void autoAck(Record record);

    void autoAckError();

    void autoAckErrorAtOffset(long offset);

    void manualAckError();

    void manualAckErrorAtOffset(long offset);

     void manualAck(Record record, int counter);
}
