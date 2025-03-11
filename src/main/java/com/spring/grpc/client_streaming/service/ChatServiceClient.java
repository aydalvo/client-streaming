package com.spring.grpc.client_streaming.service;

import com.proto.generated.banking.ChatMessage;
import com.proto.generated.banking.ChatResponse;
import com.proto.generated.banking.ChatServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import io.grpc.stub.StreamObserver;

@Service
public class ChatServiceClient {

//    @GrpcClient("grpc-streaming-service")
//    ChatServiceGrpc.ChatServiceBlockingStub synchronousClient;
//
//    @GrpcClient("grpc-streaming-service")
//    ChatServiceGrpc.ChatServiceStub asynchronousClient;

    public void sendMessage() { // Create the channel

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        // Create an asynchronous stub
        ChatServiceGrpc.ChatServiceStub asyncStub = ChatServiceGrpc.newStub(channel);

        StreamObserver<ChatResponse> responseObserver = new StreamObserver<ChatResponse>() {
            @Override
            public void onNext(ChatResponse chatResponse) {
                // Handle the server's response
                System.out.println("Received response from: " + chatResponse.getSender());
                System.out.println("Response: " + chatResponse.getMessage());
            }

            @Override
            public void onError(Throwable throwable) {
                // Handle any errors
            }

            @Override
            public void onCompleted() {
                // Complete the communication
                channel.shutdown();
            }
        };

        // Create the ChatMessage
        ChatMessage chatMessageHi = ChatMessage.newBuilder().setSender("client").setMessage("Hi").build();
        ChatMessage chatMessageBalance = ChatMessage.newBuilder().setSender("client")
                .setMessage("I need to know my account balance").build();
        ChatMessage chatMessageOTP5050 = ChatMessage.newBuilder().setSender("client").setMessage("5050").build();
        ChatMessage chatMessageInvalidOTP = ChatMessage.newBuilder().setSender("client").setMessage("1234").build();

        // Send the chat messages to the server
        asyncStub.startChat(responseObserver).onNext(chatMessageHi);
        asyncStub.startChat(responseObserver).onNext(chatMessageBalance);
        asyncStub.startChat(responseObserver).onNext(chatMessageOTP5050);
        asyncStub.startChat(responseObserver).onNext(chatMessageInvalidOTP);
    }
}
