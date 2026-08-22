package com.github.cafeduke.learn.microservices.limitsservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("limits-service")
public class LimitsConfigBean
{
  private int minimum = 0;

  private int maximum = 0;

  protected LimitsConfigBean()
  {

  }

  public int getMinimum()
  {
    return minimum;
  }

  public void setMinimum(int minimum)
  {
    this.minimum = minimum;
  }

  public int getMaximum()
  {
    return maximum;
  }

  public void setMaximum(int maximum)
  {
    this.maximum = maximum;
  }
}
