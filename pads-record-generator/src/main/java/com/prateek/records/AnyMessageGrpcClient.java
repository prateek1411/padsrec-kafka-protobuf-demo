package com.prateek.records;

import com.google.protobuf.Any;
import com.prateek.common.message.protobuf.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AnyMessageGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(AnyMessageGrpcClient.class);

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9001)
                .usePlaintext(true)
                .build();

        RecordServiceGrpc.RecordServiceBlockingStub stub = RecordServiceGrpc.newBlockingStub(channel);

        PadsRecord padsRecord = PadsRecord.newBuilder().addAdditionalinfo("Bla Bla Bla ...").build();


        RecordResponse anyResponce = stub.sendRequest(Record.newBuilder().setChild(Any.pack(padsRecord)).build());

        logger.info(anyResponce.getResponse());
        channel.shutdown();
    }
}
