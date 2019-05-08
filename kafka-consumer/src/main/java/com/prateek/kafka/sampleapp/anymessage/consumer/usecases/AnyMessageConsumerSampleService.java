package com.prateek.kafka.sampleapp.anymessage.consumer.usecases;


import com.prateek.common.message.protobuf.AnyMessage;

/**
 * In this consumer package, there's no implementation class for this interface because it will be mocked.
 */
public interface AnyMessageConsumerSampleService {
    void autoAck(AnyMessage anyMessage);

    void manualAck(AnyMessage anyMessage);

    void autoAckError();

    void autoAckErrorAtOffset(long offset);

    void manualAckError();

    void manualAckErrorAtOffset(long offset);
}
