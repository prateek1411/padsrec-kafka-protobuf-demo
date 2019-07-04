package com.prateek.kafka.nobill.record;

import com.google.protobuf.Any;
import com.sinch.common.message.protobuf.Record;
import com.sinch.common.message.protobuf.PadsRecord;
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
public class RecordController {
    private static final Logger LOG = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private RecordProducerService anyProducerService;


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
        Record record = Record.newBuilder().setChild(any).build();
        anyProducerService.send(record);
    }
}
