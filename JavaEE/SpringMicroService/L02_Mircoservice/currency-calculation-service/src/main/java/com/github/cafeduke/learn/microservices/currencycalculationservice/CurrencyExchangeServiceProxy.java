package com.github.cafeduke.learn.microservices.currencycalculationservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The FeignClient name is typically the application name (See spring.application.name in application.properties) of the target service.
 *
 */
// @FeignClient(name = "currency-exchange-service", url = "localhost:8000")
@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy
{
  /**
   * This method of the proxy
   * - Needs to invoke the currency-exchange microservice passing the 'from' and 'to'
   * - The resultant JSON needs to be mapped to CurrencyCalcuationBean
   * 
   * @param from
   * @param to
   * @return
   */
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyCalculationBean getExchangeValue(@PathVariable String from, @PathVariable String to);

}
