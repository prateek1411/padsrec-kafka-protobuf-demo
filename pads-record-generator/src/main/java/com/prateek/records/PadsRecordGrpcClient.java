package com.prateek.records;

import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.PadsRecordServiceGrpc;
import com.prateek.common.message.protobuf.PadsResponce;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PadsRecordGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(PadsRecordGrpcClient.class);

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext(true)
                .build();

        PadsRecordServiceGrpc.PadsRecordServiceBlockingStub stub = PadsRecordServiceGrpc.newBlockingStub(channel);

        PadsResponce padsResponce = stub.sendRequest(PadsRecord.newBuilder().addAdditionalinfo("Bla Bla Bla ..").build());

        logger.info(padsResponce.getResponce());
        channel.shutdown();
    }
}
