package com.prateek.kafka.nobill.record.consumer.elk;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import javax.annotation.PreDestroy;
import java.io.IOException;


@Component
public class ElkConfig {

    @Bean
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("172.16.101.220", 9200, "http")));
    }

    @Bean
    @PreDestroy
    public String closeEsClient() {

        try {
            this.client().close();
            return "Closed";

        } catch (IOException e) {

            System.out.println("Error while closing elasticsearch client");
            return "Error" +
                    "";
        }

    }
}
