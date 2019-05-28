package com.prateek.kafka.nobill.record;

import com.prateek.common.message.protobuf.*;
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
    public void sendRequest(Record request,
                            StreamObserver<RecordResponse> responseObserver) {

        recordProducerService.send(request);
        RecordResponse anyResponse = RecordResponse.newBuilder().setResponse("Success").build();
        logger.info(anyResponse.getResponse());
            responseObserver.onNext(anyResponse);
            responseObserver.onCompleted();
        }
    }

