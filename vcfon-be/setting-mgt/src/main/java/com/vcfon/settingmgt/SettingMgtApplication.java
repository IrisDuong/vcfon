package com.vcfon.settingmgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class SettingMgtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettingMgtApplication.class, args);
	}

}
