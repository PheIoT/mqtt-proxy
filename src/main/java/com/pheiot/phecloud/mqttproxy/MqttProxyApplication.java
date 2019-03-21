package com.pheiot.phecloud.mqttproxy;

import com.pheiot.phecloud.mqttproxy.listener.MessageEventListener;
import com.pheiot.phecloud.utils.EventBusUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

//	@Autowired
//	private static MessageEventListener messageEventListener;

	public static void main(String[] args) throws Exception {

//		EventBusUtils.register(messageEventListener);

		SpringApplication.run(MqttProxyApplication.class, args);
	}

}
