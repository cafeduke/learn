package com.github.cafeduke.learn.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ExchangeValue
{
  @Id
  private Long id;

  /**
   * We cannot have an SQL column name as 'from' as it is an SQL keyword.
   */
  @Column(name = "currency_from")
  private String from;

  @Column(name = "currency_to")
  private String to;

  @Column(name = "conversion_multiple")
  private BigDecimal conversionMultiple;

  /**
   * This is not mapped to database
   * The environment details must indicate the server sending the response.
   */
  private String env;

  protected ExchangeValue()
  {

  }

  protected ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple)
  {
    super();
    this.id = id;
    this.from = from;
    this.to = to;
    this.conversionMultiple = conversionMultiple;
  }

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getFrom()
  {
    return from;
  }

  public void setFrom(String from)
  {
    this.from = from;
  }

  public String getTo()
  {
    return to;
  }

  public void setTo(String to)
  {
    this.to = to;
  }

  public BigDecimal getConversionMultiple()
  {
    return conversionMultiple;
  }

  public void setConversionMultiple(BigDecimal conversionMultiple)
  {
    this.conversionMultiple = conversionMultiple;
  }

  public String getEnv()
  {
    return env;
  }

  public void setEnv(String env)
  {
    this.env = env;
  }
}
