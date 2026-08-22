package com.github.cafeduke.learn.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIGatewayConfiguration
{
  @Bean
  public RouteLocator gatewayRouter(RouteLocatorBuilder builder)
  {
    return builder.routes()
      .route(p -> p.path("/get")
        .filters(f -> f.addRequestHeader("MyClientId", "Raghu").addRequestParameter("name", "Raghu"))
        .uri("http://httpbin.org"))
      .route(p -> p.path("/snoop")
        .filters(f -> f.rewritePath("/snoop", "/DukeApp/Snoop.jsp").addRequestHeader("MyClientId", "Raghu").addRequestParameter("name", "Raghu"))
        .uri("http://localhost:18801"))
      .route(p -> p.path("/currency-calculator/**")
        .uri("lb://currency-calculation-service"))
      .route(p -> p.path("/currency-exchange/**")
        .uri("lb://currency-exchange-service"))
      .build();
  }
}
