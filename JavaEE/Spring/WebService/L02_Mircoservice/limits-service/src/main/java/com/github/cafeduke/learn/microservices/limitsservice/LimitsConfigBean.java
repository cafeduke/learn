package com.github.cafeduke.learn.microservices.limitsservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("limits-service")
public class LimitsConfigBean
{
  private int minimum;
  
  private int maximum;
  
  protected LimitsConfigBean()
  {
    
  }
  
  protected LimitsConfigBean(int minimum, int maximum)
  {
    super();
    this.minimum = minimum;
    this.maximum = maximum;
  }

  public void setMinimum(int minimum)
  {
    this.minimum = minimum;
  }

  public void setMaximum(int maximum)
  {
    this.maximum = maximum;
  }

  public int getMinimum()
  {
    return minimum;
  }
  
  public int getMaximum()
  {
    return maximum;
  } 
}
