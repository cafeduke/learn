package com.github.cafeduke.learn.rest;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class RestfulWebserviceApplication
{
   public static void main(String[] args)
   {
      SpringApplication.run(RestfulWebserviceApplication.class, args);
   }
   
   @Bean
   public LocaleResolver getLocaleResolver () 
   {
     // Use AcceptHeaderLocaleResolver over SessionLocaleResolver as it's more accurate!?
     // SessionLocaleResolver localeResolver = new SessionLocaleResolver();
     
     AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
     localeResolver.setDefaultLocale(Locale.ENGLISH);
     return localeResolver;
   }
   
   /**
    * Message source easy way
    * -----------------------
    * Instead of the below code just add 'spring.messages.basename=messages' in application.properties
    */
 
//  Message source hard way
//  -----------------------  
//  @Bean("messageSource")
//  public ResourceBundleMessageSource getResourceBundleMessageSource ()
//  {
//    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//    messageSource.setBasename("messages");
//    return messageSource;
//  }
}
