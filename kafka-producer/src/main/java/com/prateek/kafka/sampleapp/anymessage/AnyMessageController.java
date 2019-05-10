package com.prateek.kafka.sampleapp.anymessage;

import com.google.protobuf.Any;
import com.prateek.common.message.protobuf.AnyMessage;
import com.prateek.common.message.protobuf.PadsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AnyMessageController {
    private static final Logger LOG = LoggerFactory.getLogger(AnyMessageController.class);

    @Autowired
    private AnyMessageProducerService anyProducerService;

    @Autowired
    private PadsRecordGenerator padsRecordGenerator;


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

    @RequestMapping(value = "/records", method = RequestMethod.POST)
    //public void sendPadsrecord() {
    public void sendPadsrecord(@RequestBody String jsonObject) {
        LOG.info("Send Pads Record: "+ jsonObject);
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Map<String, Object> jsonMap = jsonParser.parseMap(jsonObject);;
        Set<String> reports = (Set<String>)jsonMap.keySet();
        LOG.info("Key set: "+ reports);
        Map<String, Object> fields = (HashMap<String,Object>) jsonMap.get("fields");
        LOG.info("Fields value : "+fields);
        Iterator I = fields.keySet().iterator();
        PadsRecord padsRecord = PadsRecord.newBuilder().build();
        while (I.hasNext()) {
            String key = I.next().toString();
            Object value = fields.get(key);
            if (value!=null) {
                try {
                    padsRecord = padsRecord.toBuilder().setField(PadsRecord.getDescriptor().findFieldByName(key), value)
                            .build();
                } catch (Exception e){
                    LOG.info("Fields {} Value {} has exception {}",key,value,e.getMessage());
                }
            }
        }
        Any any = Any.pack(padsRecord);
        AnyMessage anyMessage = AnyMessage.newBuilder().setPadsrecord(any).build();
        anyProducerService.send(anyMessage);


    }
}
