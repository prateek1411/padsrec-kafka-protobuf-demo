package com.prateek.kafka.sampleapp.anymessage;

import com.prateek.common.message.protobuf.PadsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PadsRecordGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PadsRecordGenerator.class);

    @Bean
    public List<PadsRecord> loadPadsRecords() {

        List<PadsRecord> padsRecord = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            padsRecord.add(PadsRecord.newBuilder().addAdditionalinfo("I am coming from somewhere else").build());
        }
        return padsRecord;
    }

}
