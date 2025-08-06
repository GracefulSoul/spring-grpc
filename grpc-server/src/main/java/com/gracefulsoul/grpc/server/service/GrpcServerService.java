package com.gracefulsoul.grpc.server.service;

import org.springframework.stereotype.Service;
import com.gracefulsoul.grpc.lib.proto.HelloGrpc;
import com.gracefulsoul.grpc.lib.proto.Reply;
import com.gracefulsoul.grpc.lib.proto.Request;

import io.grpc.stub.StreamObserver;

@Service
public class GrpcServerService extends HelloGrpc.HelloImplBase {

	@Override
	public void sayHello(Request request, StreamObserver<Reply> responseObserver) {
		if (request.getName().startsWith("error")) {
			throw new IllegalArgumentException("Bad name: " + request.getName());
		}
		if (request.getName().startsWith("internal")) {
			throw new RuntimeException();
		}
		Reply reply = Reply.newBuilder().setMessage("Hello " + request.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void streamHello(Request request, StreamObserver<Reply> responseObserver) {
		int count = 0;
		while (count < 10) {
			Reply reply = Reply.newBuilder().setMessage("Hello " + request.getName()).build();
			responseObserver.onNext(reply);
			count++;
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				responseObserver.onError(e);
				return;
			}
		}
		responseObserver.onCompleted();
	}

}