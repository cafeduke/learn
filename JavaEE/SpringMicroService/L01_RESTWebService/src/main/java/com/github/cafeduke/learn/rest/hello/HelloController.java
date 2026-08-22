package com.github.cafeduke.learn.rest.hello;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.github.cafeduke.learn.rest.Util;

@RestController
public class HelloController
{
  @Autowired
  private MessageSource messageSource; 
  
  @GetMapping(path = "/hello")
  public String sayHello()
  {
    return "Hello" + Util.LINE_SEP;
  }

  @GetMapping(path = "/hello-bean")
  public HelloBean sayHelloBean()
  {
    return new HelloBean("Hello from bean");
  }
  
  /**
   * Locale the hard way
   * -------------------
   * The locale is derived from 'Accept-Language' header and 'required=false' makes the header optional.
   * If the header is not present, the default Locale configured must be used.
   * The {@code RestfulWebserviceApplication.getLocaleResolver} configures default Locale. 
   * 
   * @param locale The language_Country code provided by client (Eg: en_US)
   * @return
   */
  @GetMapping(path = "/hello-g11n-hard")
  public String sayHelloG11nHard(@RequestHeader(name="Accept-Language", required=false) Locale locale)
  {
    return messageSource.getMessage("my.greeting", null, locale) + Util.LINE_SEP;
  }
  
  /**
   * Locale the easy way
   * -------------------
   * The locale is derived from 'Accept-Language' header and 'required=false' makes the header optional.
   * If the header is not present, the default Locale configured must be used.
   * The {@code RestfulWebserviceApplication.getLocaleResolver} configures default Locale. 
   * 
   * @param locale The language_Country code provided by client (Eg: en_US)
   * @return
   */
  @GetMapping(path = "/hello-g11n")
  public String sayHelloG11n()
  {
    return messageSource.getMessage("my.greeting", null, LocaleContextHolder.getLocale()) + Util.LINE_SEP;
  }  
  
}
