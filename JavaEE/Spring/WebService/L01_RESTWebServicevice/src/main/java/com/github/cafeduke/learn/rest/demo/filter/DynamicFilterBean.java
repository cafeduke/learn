package com.github.cafeduke.learn.rest.demo.filter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * This class is used demonstrate the filtering capabilities.
 */
@JsonFilter("DemoDynamicFilter")
public class DynamicFilterBean
{
  private String name = null;
  
  private String city = null;
  
  private int age = 0;
  
  private int salary = 0;

  public DynamicFilterBean(String name, String city, int age, int salary)
  {
    super();
    this.name = name;
    this.city = city;
    this.age = age;
    this.salary = salary;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getCity()
  {
    return city;
  }

  public void setCity(String city)
  {
    this.city = city;
  }

  public int getAge()
  {
    return age;
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public int getSalary()
  {
    return salary;
  }

  public void setSalary(int salary)
  {
    this.salary = salary;
  }  
}
