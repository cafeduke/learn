package com.github.cafeduke.learn.rest.demo.version;

/**
 * Version v2 of a person implementation.
 */
public class PersonV2Bean
{
  private PersonV2Bean.Name name = null;
  
  private String city = null;
  
  private int age = 0;
  
  private int salary = 0;  

  public PersonV2Bean(Name name, String city, int age, int salary)
  {
    super();
    this.name = name;
    this.city = city;
    this.age = age;
    this.salary = salary;
  }

  public Name getName()
  {
    return name;
  }

  public void setName(Name name)
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

  protected static class Name
  {
    private String firstName = null;
    private String lastName = null;
    
    public Name (String firstName, String lastName)
    {
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName()
    {
      return firstName;
    }

    public void setFirstName(String firstName)
    {
      this.firstName = firstName;
    }

    public String getLastName()
    {
      return lastName;
    }

    public void setLastName(String lastName)
    {
      this.lastName = lastName;
    }
  };
  
}
