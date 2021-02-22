package com.github.cafeduke.learn.microservices.currencycalculationservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Config with hard-coded service location
// Feign provides the ability to add proxy and abstract the target service details.
// @FeignClient(name = "currency-exchange-service", url = "localhost:8000")

// Ribbon is used to loadbalance amoung multiple servers (provding same service)
@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy
{
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyCalculationBean getExchangeValue(@PathVariable String from, @PathVariable String to);
}
