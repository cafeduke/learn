package com.github.cafeduke.learn.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueService extends JpaRepository<ExchangeValue, Long>
{
  /**
   * Just by adding the interface definition, JPA shall provide the implementation!
   * 
   * @param from The 'from' currency field
   * @param to The 'to' currency field
   * @return The ExchangeValue entity object.
   */
  ExchangeValue findByFromAndTo(String from, String to);
}
