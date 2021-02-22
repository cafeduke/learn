package com.github.cafeduke.learn.microservices.currencycalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.github.cafeduke.learn.microservices.currencycalculationservice")
@EnableDiscoveryClient
public class CurrencyCalculationServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(CurrencyCalculationServiceApplication.class, args);
  }
}
