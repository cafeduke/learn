package com.github.cafeduke.learn.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController
{
  /**
   * Multiple instances of currency exchange controller shall be running.
   * The server port shall be fetched from the env to identify the specific instance that is being used
   */
  @Autowired
  private Environment env;

  @Autowired
  private ExchangeValueService exchangeValueService;

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public ExchangeValue getExchangeValue(@PathVariable String from, @PathVariable String to)
  {
    ExchangeValue exchangeValue = exchangeValueService.findByFromAndTo(from, to);
    exchangeValue.setPort(Integer.valueOf(env.getProperty("local.server.port")));
    return exchangeValue;
  }
}
