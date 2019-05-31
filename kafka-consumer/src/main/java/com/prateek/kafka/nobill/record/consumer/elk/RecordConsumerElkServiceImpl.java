package com.prateek.kafka.nobill.record.consumer.elk;

import com.google.protobuf.InvalidProtocolBufferException;
import com.prateek.common.message.protobuf.PadsRecord;
import com.prateek.common.message.protobuf.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Service
public class RecordConsumerElkServiceImpl implements RecordConsumerElkService {
    /**
     * This is recommend by this: https://www.slf4j.org/faq.html#declared_static
     */
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public ElkRepository elkRepository;

    @Override
    public ElkRecord save(ElkRecord elkrecord) {
        return elkRepository.save(elkrecord);
    }

    @Override
    public Optional<ElkRecord> findOne(String id) {
        return elkRepository.findById(id);
    }

    @Override
    public Iterable<ElkRecord> findAll() {
        return elkRepository.findAll();
    }
}
