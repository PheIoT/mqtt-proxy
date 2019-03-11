package com.pheiot.phecloud.mqttproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ServletComponentScan
//@EnableJpaAuditing
@ComponentScan(basePackages = {"com.pheiot"})
public class MqttProxyApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MqttProxyApplication.class, args);
	}

}
