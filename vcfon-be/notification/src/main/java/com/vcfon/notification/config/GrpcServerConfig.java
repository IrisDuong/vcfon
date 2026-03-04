package com.vcfon.notification.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vcfon.notification.mail.MailGrpcServiceImpl;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

@Configuration
public class GrpcServerConfig {

	@Value("${GRPC_PORT_NOTIFICATION}")
	private int grpcPortNotification;
	
	@Bean(initMethod = "start", destroyMethod = "shutdown")
	public Server grpcServer(List<BindableService> services) {
		NettyServerBuilder server  = NettyServerBuilder.forPort(grpcPortNotification);
		services.forEach(server::addService);
		return server.build();
	}
}
