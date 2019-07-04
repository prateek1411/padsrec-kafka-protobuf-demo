package com.prateek.records;

import com.google.protobuf.Any;
import com.sinch.common.message.protobuf.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


public class AnyMessageGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(AnyMessageGrpcClient.class);

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9001)
                .usePlaintext(true)
                .build();

        final CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<RecordResponse> responseObserver = new StreamObserver<RecordResponse>() {

            @Override
            public void onNext(RecordResponse response) {

            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gRPC Failed");
                System.out.println(t.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Finished gRPC");
                finishLatch.countDown();
            }

        };

        RecordServiceGrpc.RecordServiceStub stub = RecordServiceGrpc.newStub(channel);

        //PadsRecord padsRecord = PadsRecord.newBuilder().addAdditionalinfo("Bla Bla Bla ...").build();
        StreamObserver<Record> requestObserver = stub.sendRequest(responseObserver);

        channel.shutdown();
    }
}
