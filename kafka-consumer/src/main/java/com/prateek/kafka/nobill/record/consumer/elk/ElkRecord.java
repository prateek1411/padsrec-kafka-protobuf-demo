package com.prateek.kafka.nobill.record.consumer.elk;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="records")
@Document(indexName = "record_index", type = "elkrecord")
public class ElkRecord {

    public ElkRecord() {

    }

    public ElkRecord(String id, String record) {
        Id = id;
        this.record = record;
    }

    @Id
    @Column(name="id")
   // @Field(type = FieldType.Auto, store = true)
    private String Id;


    @Column(name="record")
    //@Field(type = FieldType.Auto, store = true)
    private String record;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }



}
