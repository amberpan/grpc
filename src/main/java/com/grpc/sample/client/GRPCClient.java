package com.grpc.sample.client;

import com.google.common.base.Stopwatch;
import com.grpc.sample.HelloRequest;
import com.grpc.sample.HelloResponse;
import com.grpc.sample.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class GRPCClient {
    public static void main(String[] args) {
        Stopwatch watch = Stopwatch.createStarted();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9001)
                .usePlaintext(true)
                .build();
        HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

        System.out.println("Time taken to create channel and stub: "+watch.elapsed(TimeUnit.MILLISECONDS));

        for (int i = 0; i < 10000 ; i++) {
            Stopwatch stop = Stopwatch.createStarted();
            HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                    .setFirstName("Baeldung")
                    .setLastName("gRPC")
                    .build());
            System.out.println("Time taken="+stop.stop().elapsed(TimeUnit.MILLISECONDS));
        }

        channel.shutdown();
    }
}
