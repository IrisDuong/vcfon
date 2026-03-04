package com.vcfon.apigateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vcfon.grpc.usermgt.SysUserProtoServiceGrpc;
import com.vcfon.grpc.usermgt.SysUserProtoServiceGrpc.SysUserProtoServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {
	
	private final DiscoveryClient discoveryClient;
	
	@Value("${GRPC_PORT_USER_MGT}")
	private int grpcPortUserMgt;

	public GrpcClientConfig(DiscoveryClient discoveryClient) {
		super();
		this.discoveryClient = discoveryClient;
	}
	
	@Bean
	public ManagedChannel userServiceChanel() {
		List<ServiceInstance> serviceInstances = discoveryClient.getInstances("user-mgt");
		if(serviceInstances.isEmpty())
			throw new IllegalStateException("No instance of user-management-service");
		return ManagedChannelBuilder.forAddress(serviceInstances.get(0).getHost(), grpcPortUserMgt).usePlaintext().build();
	}

	@Bean
	public SysUserProtoServiceBlockingStub sysUserProtoServiceBlockingStub(ManagedChannel userServiceChanel) {
		return SysUserProtoServiceGrpc.newBlockingStub(userServiceChanel);
	}
}
