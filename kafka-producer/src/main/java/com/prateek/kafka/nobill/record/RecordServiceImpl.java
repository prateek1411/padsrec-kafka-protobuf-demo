package com.prateek.kafka.nobill.record;

import com.sinch.common.message.protobuf.*;
import com.prateek.kafka.nobill.GrpcService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class RecordServiceImpl extends RecordServiceGrpc.RecordServiceImplBase implements GrpcService {
    private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private RecordProducerService recordProducerService;

    @Override
    public StreamObserver<Record> sendRequest(final StreamObserver<RecordResponse> responseObserver) {

        return new StreamObserver<Record>() {
            @Override
            public void onNext(Record record) {
            recordProducerService.send(record);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gRPC Failed");
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Finished gRPC");
            }
            /*
            recordProducerService.send(request);
            RecordResponse anyResponse = RecordResponse.newBuilder().setResponse("Success").build();
        logger.info(anyResponse.getResponse());
            responseObserver.onNext(anyResponse);
            responseObserver.onCompleted();

             */

        };
    }
    }

