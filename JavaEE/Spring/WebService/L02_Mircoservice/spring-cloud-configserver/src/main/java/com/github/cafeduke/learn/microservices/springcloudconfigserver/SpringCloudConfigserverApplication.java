package com.github.cafeduke.learn.microservices.springcloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringCloudConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigserverApplication.class, args);
	}

}
