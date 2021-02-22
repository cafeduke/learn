package com.github.cafeduke.learn.microservices.currencycalculationservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyCalculationServiceController
{
  @Autowired
  private CurrencyExchangeServiceProxy proxy;

  @GetMapping("/currency-calculator/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyCalculationBean getCalculation(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
  {
    CurrencyCalculationBean currCalculationBean = proxy.getExchangeValue(from, to);
    currCalculationBean.setQuantity(quantity);
    currCalculationBean.setTotalCalculatedAmount(quantity.multiply(currCalculationBean.getConversionMultiple()));
    return currCalculationBean;
  }
}
