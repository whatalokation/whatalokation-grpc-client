package com.whatalokation.grpc.client.greeter;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.whatalokation.grpc.client.greeter.HealthCheckRequest;
import com.whatalokation.grpc.client.greeter.HealthCheckResponse;
import com.google.protobuf.ByteString;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

/**
 * A client that requests Greeter service from the Greeter Server.
 */
public class GreeterClient {

	private static final Logger logger = Logger.getLogger("Whatalokation");

	private final ManagedChannel channel;
	private final GreeterGrpc.GreeterBlockingStub blockingStub;
	private final GreeterGrpc.GreeterStub asyncStub;
	private long totalBytesPut = 0;

	/** Construct client connecting to Greeter server at {@code host:port}. */
	public GreeterClient(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port)
				// Channels are secure by default (via SSL/TLS). For the example
				// we disable TLS to avoid
				// needing certificates.
				.usePlaintext(true));
		logger.setLevel(Level.WARNING);
	}

	/**
	 * Construct client for accessing Greeter server using the existing channel.
	 */
	GreeterClient(ManagedChannelBuilder<?> channelBuilder) {
		channel = channelBuilder.build();
		blockingStub = GreeterGrpc.newBlockingStub(channel);
		asyncStub = GreeterGrpc.newStub(channel);

	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/** check health of the server. */
	public String healthcheck() {
		logger.info("running greeter check health ");
		HealthCheckRequest request = HealthCheckRequest.newBuilder().build();
		HealthCheckResponse response = null;
		try {
			response = blockingStub.healthCheck(request);

		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
			throw e;
		}
		logger.info("Health Check Response for Greeter Service is: " + response.getMessage());
		return response.getMessage();
	}

	
	public static void main(String[] args) throws Exception {
		GreeterClient client = new GreeterClient("greeter-staging.endpoint.whatalokationdev.com", 80);
		try {

			client.healthcheck();
		} finally {
			client.shutdown();
		}
	}

}