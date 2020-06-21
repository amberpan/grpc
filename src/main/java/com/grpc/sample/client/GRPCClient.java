package com.grpc.sample.client;

import com.google.common.base.Stopwatch;
import com.grpc.sample.HelloRequest;
import com.grpc.sample.HelloResponse;
import com.grpc.sample.HelloServiceGrpc;
import com.grpc.sample.server.GRPCServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GRPCClient {
    public static void main(String[] args) throws SSLException {
        Stopwatch watch = Stopwatch.createStarted();
        File cert = new File(GRPCServer.class.getClassLoader().getResource("certs/cert.pem").getFile());

        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 9002)
                .sslContext(GrpcSslContexts.forClient()
                .trustManager(cert).build())
                .build();
        HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

        System.out.println("Time taken to create channel and stub: "+watch.elapsed(TimeUnit.MILLISECONDS));

        for (int i = 0; i < 10000 ; i++) {
            Stopwatch stop = Stopwatch.createStarted();
            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                    .setFirstName("Hello")
                    .setLastName("World")
                    .build());
            System.out.println("Time taken="+stop.stop().elapsed(TimeUnit.MILLISECONDS));
        }

        channel.shutdown();
    }
}
