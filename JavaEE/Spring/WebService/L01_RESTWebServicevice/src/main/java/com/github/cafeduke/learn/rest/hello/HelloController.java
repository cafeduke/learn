package com.github.cafeduke.learn.rest.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
   @GetMapping(path="/hello")
   public String sayHello ()
   {
      return "Hello";
   }
   
   @GetMapping(path="/hello-bean")
   public HelloBean sayHelloBean ()
   {
      return new HelloBean("Hello from bean");
   }
}
