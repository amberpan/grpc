package com.grpc.sample.server.api;

import com.grpc.sample.HelloRequest;
import com.grpc.sample.HelloResponse;
import com.grpc.sample.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        try {
            System.out.println("Received request...");
//            Thread.sleep(5);
            HelloResponse response = HelloResponse.newBuilder().setGreeting("Hello from gRPC").build();
            responseObserver.onNext(response);
            System.out.println("Completing request");
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
