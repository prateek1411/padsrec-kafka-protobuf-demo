package com.prateek.kafka.sampleapp.anymessage;

import com.google.protobuf.Any;
import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.message.protobuf.PadsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnyMessageController {
    private static final Logger LOG = LoggerFactory.getLogger(AnyMessageController.class);

    @Autowired
    private AnyMessageProducerService anyProducerService;

    @RequestMapping(value = "/any", method = RequestMethod.GET)
    public void sendRequest(){
        LOG.info("Send message: ");
        PadsRecord record = PadsRecord.newBuilder().setSubscriberip(2)
                .addAdditionalinfo("I am PADS Record").build();
        Any any = Any.pack(record);
        AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).build();
        anyProducerService.send(anyMessage);
    }
}
