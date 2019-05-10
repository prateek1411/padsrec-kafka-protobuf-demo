package com.prateek.kafka.sampleapp.anymessage;

import com.google.protobuf.Any;
import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.PadsRecordServiceGrpc;
import com.prateek.common.message.protobuf.PadsResponce;
import com.prateek.kafka.sampleapp.GrpcService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Service
public class PadsRecordServiceImpl extends PadsRecordServiceGrpc.PadsRecordServiceImplBase implements GrpcService {
    private static final Logger logger = LoggerFactory.getLogger(PadsRecordServiceImpl.class);

    @Autowired
    private AnyMessageProducerService anyProducerService;

    @Override
    public void sendRequest(PadsRecord request,
                            StreamObserver<PadsResponce> responseObserver) {

        // producer.sendMessage("Hello, World!");
        logger.info(request.toBuilder().getAdditionalinfo(0));
        Any any = Any.pack(request);
        AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).build();
        anyProducerService.send(anyMessage);
        PadsResponce padsResponce = PadsResponce.newBuilder().setResponce("Success").build();
        logger.info(padsResponce.getResponce());
            responseObserver.onNext(padsResponce);
            responseObserver.onCompleted();
        }
    }

