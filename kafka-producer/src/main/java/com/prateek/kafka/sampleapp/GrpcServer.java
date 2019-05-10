package com.prateek.kafka.sampleapp;

import com.prateek.kafka.sampleapp.anymessage.PadsRecordServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Component
@Service
public class GrpcServer {
  /*  @Override
    public void run(String... args) throws Exception {

        Server server = ServerBuilder
                .forPort(9000)
                .addService(new PadsRecordServiceImpl())
                .build();
        try {
            server.start();
               } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

   */
    @Autowired
    private List<GrpcService> services;

    private Server server;

    public void start(int port) throws IOException, InterruptedException {

        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        for (GrpcService grpcService : services) {
            if (!(grpcService instanceof BindableService)) {
                throw new RuntimeException("GrpcService should only used for grpc BindableService; found wrong usage of GrpcService for service: " + grpcService.getClass().getSimpleName());
            }
            serverBuilder.addService((BindableService) grpcService);
        }

        this.server = ((ServerBuilder<?>) serverBuilder)
                .addService(ProtoReflectionService.newInstance())
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            stop();
            System.err.println("*** server shut down");
        }));

        blockUntilShutdown();
    }

    public void stop() {
        if (this.server != null) {
            this.server.shutdown();
        }
    }

    /**
     * Wait for main method. the gprc services uses daemon threads
     * @throws InterruptedException
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (this.server != null) {
            this.server.awaitTermination();
        }
    }

}
