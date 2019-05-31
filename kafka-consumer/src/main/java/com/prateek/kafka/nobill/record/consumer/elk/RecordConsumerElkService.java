package com.prateek.kafka.nobill.record.consumer.elk;


import com.prateek.common.message.protobuf.Record;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * In this consumer package, there's no implementation class for this interface because it will be mocked.
 */
public interface RecordConsumerElkService {

    ElkRecord save(ElkRecord record);

    Optional<ElkRecord> findOne(String id);

    Iterable<ElkRecord> findAll();
}
