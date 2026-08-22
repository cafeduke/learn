package com.github.cafeduke.learn.microservices.apigateway;

import java.util.logging.Logger;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter
{
  private Logger logger = Logger.getLogger(getClass().getName());

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
  {
    logger.info(String.format("Path of request recieved = {}", exchange.getRequest().getPath()));
    return chain.filter(exchange);
  }
}
