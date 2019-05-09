package com.prateek.loadgenerator.record;

import com.google.protobuf.Any;
import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.kafka.sampleapp.anymessage.AnyMessageProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class AnyMessageController {
    private static final Logger LOG = LoggerFactory.getLogger(AnyMessageController.class);

    @Autowired
    private AnyMessageProducerService anyProducerService;

    @Autowired
    private PadsRecordGenerator padsRecordGenerator;

    @RequestMapping(value = "/any", method = RequestMethod.GET)
    public void sendRequest() {
        LOG.info("Send message: ");
        List<PadsRecord> recordList = new ArrayList<>();
        recordList = padsRecordGenerator.loadPadsRecords();
        Iterator<PadsRecord> I = recordList.iterator();
        Long index = 0L;
        while (I.hasNext()) {
            Any any = Any.pack(I.next());
            AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).setMessage(index.toString()).build();
            anyProducerService.send(anyMessage);
            index++;
        }
        //PadsRecord record = PadsRecord.newBuilder().setSubscriberip(2)
        //        .addAdditionalinfo("I am PADS Record").build();
        //Any any = Any.pack(record);
        //AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).build();
        //anyProducerService.send(anyMessage);
    }

    @RequestMapping(value = "/padsrecords", method = RequestMethod.POST)
    public void sendPadsrecord(@RequestBody PadsRecord padsRecord) {
        LOG.info("Send pads Reord: ");
        //PadsRecord record = PadsRecord.newBuilder().setSubscriberip(2)
        //        .addAdditionalinfo("I am PADS Record").build();
        Any any = Any.pack(padsRecord);
        AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).build();
        anyProducerService.send(anyMessage);
    }
}
