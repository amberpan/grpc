package com.grpc.sample.server;

import com.grpc.sample.server.api.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9001)
                .addService(new HelloServiceImpl()).build();
        System.out.println("Starting server..."+server);
        server.start();
        System.out.println("Server started...");
        server.awaitTermination();
    }
}
