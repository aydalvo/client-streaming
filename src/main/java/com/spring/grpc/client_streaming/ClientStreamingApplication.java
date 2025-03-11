package com.spring.grpc.client_streaming;

import com.spring.grpc.client_streaming.service.ChatServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ClientStreamingApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ClientStreamingApplication.class, args);
		ChatServiceClient bankService = context.getBean(ChatServiceClient.class);
		bankService.sendMessage();
	}

}
