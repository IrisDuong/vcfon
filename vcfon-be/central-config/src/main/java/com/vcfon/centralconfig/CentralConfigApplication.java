package com.vcfon.centralconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CentralConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralConfigApplication.class, args);
	}

}
