package com.github.cafeduke.learn.microservices.currencyexchangeservice;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController
{
  private Logger logger = Logger.getLogger(getClass().getName());

  @GetMapping("/test-retry")
  @Retry(name = "test-retry", fallbackMethod = "fallbackResponse")
  public String testRetry()
  {
    logger.info("Inside testRetry");
    ResponseEntity<String> entityNotFound = new RestTemplate().getForEntity("http://localhost:8080/doesNotExist", String.class);
    return entityNotFound.getBody();
  }

  @GetMapping("/test-circuit-breaker")
  @CircuitBreaker(name = "test-circuit-breaker", fallbackMethod = "fallbackResponse")
  public String testCircuitBreaker()
  {
    logger.info("Inside testCircuitBreaker");
    ResponseEntity<String> entityNotFound = new RestTemplate().getForEntity("http://localhost:8080/doesNotExist", String.class);
    return entityNotFound.getBody();
  }

  @GetMapping("/test-rate-limiter")
  @RateLimiter(name = "test-rate-limiter")
  public String testRateLimiter()
  {
    return "Hello from rate limiter";
  }

  @GetMapping("/test-bulk-head")
  @Bulkhead(name = "test-bulk-head")
  public String testBulkHead()
  {
    return "Hello from bulk head";
  }

  /**
   * This method shall be invoked if maximum retry attempts fail
   */
  public String fallbackResponse(Throwable t)
  {
    return "Hey, server is busy. Eat an apple and be back.";
  }
}
