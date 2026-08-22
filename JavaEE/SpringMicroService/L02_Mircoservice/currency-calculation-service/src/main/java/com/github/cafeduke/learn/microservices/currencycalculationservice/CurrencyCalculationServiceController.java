package com.github.cafeduke.learn.microservices.currencycalculationservice;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
    currCalculationBean.doCalulate();
    return currCalculationBean;
  }

  @GetMapping("/currency-calculator-legacy/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyCalculationBean getCalculationLegacy(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
  {
    // A map of URI parameter to its value
    Map<String, String> mapParamValue = Map.ofEntries(Map.entry("from", from), Map.entry("to", to));

    /*
     * The URL to the service that is requested is hard-coded.
     * The JSON obtained by URL, passed to getForEntity, is mapped to bean 'CurrencyCalculationBean' (Ensure the JSON response and field names in bean are identical)
     */
    ResponseEntity<CurrencyCalculationBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyCalculationBean.class, mapParamValue);
    CurrencyCalculationBean currencyCalculationBean = responseEntity.getBody();
    currencyCalculationBean.setQuantity(quantity);
    currencyCalculationBean.doCalulate();
    return currencyCalculationBean;
  }

}
