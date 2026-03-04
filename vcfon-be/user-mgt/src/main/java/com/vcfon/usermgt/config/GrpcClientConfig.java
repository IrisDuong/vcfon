package com.vcfon.usermgt.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vcfon.grpc.notification.MailProtoServiceGrpc;
import com.vcfon.grpc.notification.MailProtoServiceGrpc.MailProtoServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

	private final DiscoveryClient discoveryClient;
	
	@Value("${GRPC_PORT_NOTIFICATION}")
	private int grpcPortNotification;

	public GrpcClientConfig(DiscoveryClient discoveryClient) {
		super();
		this.discoveryClient = discoveryClient;
	}
	
	@Bean
	public ManagedChannel notificationServiceChanel() {
		List<ServiceInstance> servicesInstance = discoveryClient.getInstances("notification");
		if(servicesInstance.isEmpty())
			throw new IllegalStateException("No instance of notification-service");
		return ManagedChannelBuilder.forAddress(servicesInstance.get(0).getHost(), grpcPortNotification)
				.usePlaintext().build();
	}
	
	@Bean
	public MailProtoServiceBlockingStub mailProtoServiceBlockingStub(ManagedChannel notificationServiceChanel) {
		return MailProtoServiceGrpc.newBlockingStub(notificationServiceChanel);
	}
}
