package com.prateek.kafka.nobill.record.consumer.elk;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import javax.annotation.PreDestroy;
import java.io.IOException;


@Component
public class ElkConfig {
    private final static Logger logger = LoggerFactory.getLogger(ElkConfig.class);   @Bean
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

            logger.info("Error while closing elasticsearch client");
            return "Error" +
                    "";
        }

    }
}
