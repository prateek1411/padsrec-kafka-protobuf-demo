package com.prateek.kafka.nobill.record.consumer.elk;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElkRepository extends ElasticsearchRepository<ElkRecord, String> {
}
