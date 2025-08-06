package com.gracefulsoul.grpc.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;

import com.gracefulsoul.grpc.lib.proto.HelloGrpc;
import com.gracefulsoul.grpc.lib.proto.Request;

@SpringBootApplication
public class GrpcClientApplication {

	private static final Log LOG = LogFactory.getLog(GrpcClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);
	}

	@Bean
	HelloGrpc.HelloBlockingStub stub(GrpcChannelFactory channels) {
		return HelloGrpc.newBlockingStub(channels.createChannel("local"));
	}

	@Bean
	CommandLineRunner runner(HelloGrpc.HelloBlockingStub stub) {
		return args -> LOG.info(stub.sayHello(Request.newBuilder().setName("GracefulSoul").build()));
	}

}
