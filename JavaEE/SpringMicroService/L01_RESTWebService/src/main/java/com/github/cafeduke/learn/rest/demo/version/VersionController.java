package com.github.cafeduke.learn.rest.demo.version;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController
{  
  /* Version Using the URI */  
  
  @GetMapping("/demo/version/person/v1")
  public PersonV1Bean getPersonV1UsingURI ()
  {
    return new PersonV1Bean("Raghu.Nandan", "Bangalore", 38, 150);
  }
  
  @GetMapping("/demo/version/person/v2")
  public PersonV2Bean getPersonV2UsingURI ()
  {
    return new PersonV2Bean(new PersonV2Bean.Name("Raghu", "Nandan"), "Bangalore", 36, 70);
  }
  
  /* Version Using the query string */

  @GetMapping(value="/demo/version/person", params="version=v1")
  public PersonV1Bean getPersonV1UsingQS ()
  {
    return new PersonV1Bean("Raghu.Nandan", "Bangalore", 38, 150);
  }
  
  @GetMapping(value="/demo/version/person", params="version=v2")
  public PersonV2Bean getPersonV2UsingQS ()
  {
    return new PersonV2Bean(new PersonV2Bean.Name("Raghu", "Nandan"), "Bangalore", 36, 70);
  }
  
  /* Version Using the query header */
  
  @GetMapping(value="/demo/version/person", headers="My-Version=v1")
  public PersonV1Bean getPersonV1UsingHeader()
  {
    return new PersonV1Bean("Raghu.Nandan", "Bangalore", 38, 150);
  }
  
  @GetMapping(value="/demo/version/person", headers="My-Version=v2")
  public PersonV2Bean getPersonV2UsingHeader ()
  {
    return new PersonV2Bean(new PersonV2Bean.Name("Raghu", "Nandan"), "Bangalore", 36, 70);
  }
  
  /* Version Using the produces -- Ensure produces value is application/<custom string>+json */
  
  @GetMapping(value="/demo/version/person", produces="application/v1+json")
  public PersonV1Bean getPersonV1UsingProduces()
  {
    return new PersonV1Bean("Raghu.Nandan", "Bangalore", 38, 150);
  }
  
  @GetMapping(value="/demo/version/person", produces="application/v2+json")
  public PersonV2Bean getPersonV2UsingProduces ()
  {
    return new PersonV2Bean(new PersonV2Bean.Name("Raghu", "Nandan"), "Bangalore", 36, 70);
  }
  
}
