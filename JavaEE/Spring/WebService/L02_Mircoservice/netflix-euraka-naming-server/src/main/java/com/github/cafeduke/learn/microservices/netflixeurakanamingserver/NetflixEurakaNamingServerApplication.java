package com.github.cafeduke.learn.microservices.netflixeurakanamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixEurakaNamingServerApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(NetflixEurakaNamingServerApplication.class, args);
  }

}
