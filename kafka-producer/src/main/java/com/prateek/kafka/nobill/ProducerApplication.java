package com.prateek.kafka.nobill;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.io.IOException;

/**
 * Spring boot application to test Kafka integration.
 */
@SpringBootApplication
public class ProducerApplication {

    private final static Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

    @SuppressWarnings("resource")
    public static void main(final String[] args) throws IOException, InterruptedException {
        SpringApplication app = new SpringApplication(ProducerApplication.class);
        //app.setWebEnvironment(false);

        ConfigurableApplicationContext ctx = app.run(args);
        int port = getPort(args);
        logger.info ("GRPC Server running on the port {}",port);
        GrpcServer server = ctx.getAutowireCapableBeanFactory().createBean(GrpcServer.class);
        server.start(port);
      }

    private static int getPort(String[] args) {
        int port = 9001;
        if (args.length > 0) {
            try {
                String portNum = args[0];
                int parsedPort = Integer.parseInt(portNum);
                if (parsedPort > 0) {
                    port = parsedPort;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return port;
    }

}
