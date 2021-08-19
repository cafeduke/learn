package com.github.cafeduke.learn.microservices.currencycalculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign is used by a microservice to talk to other microservices.
 * We need to
 * - Update pom.xml to include artifact 'spring-cloud-starter-openfeign'
 * - Add the '@EnableFeignClients' annotation to the application.
 */
@SpringBootApplication
@EnableFeignClients
public class CurrencyCalculationServiceApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(CurrencyCalculationServiceApplication.class, args);
  }
}
