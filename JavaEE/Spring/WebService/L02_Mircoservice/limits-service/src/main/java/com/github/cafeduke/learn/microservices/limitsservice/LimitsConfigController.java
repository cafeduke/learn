package com.github.cafeduke.learn.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigController
{
  @Autowired
  private LimitsConfigBean config;

  @GetMapping("/limits")
  public LimitsConfigBean getLimitsConfig()
  {
    return config;
  }
}
