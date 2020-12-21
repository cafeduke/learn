# Introduction

REpresentation State Transfer (REST) is leveraging HTTP for exposing web services. 



# HATEOAS

HATEOAS (hay-t-oas) stands for Hypermedia As The Engine Of Application State. The concept of this is to provide additional related meta-data (like links) when a resource is requested. 

HATEOAS is a concept of **application architecture**. It defines the way in which application clients interact with the server, by navigating hypermedia links they find inside **resource models** returned by the server. To *implement* HATEOAS you need some standard way of representing resources -- HAL (Hypertext Application Language) is one such standard. Other standards include, link-headers, collection+JSON etc.

Add the following dependency to `pom.xml`

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

Below is a snippet of a `RestController`  namely  `UserController` . 

- When user requests `/user/{id}` we would like to provide some meta-data about the user.  In this case a link to all users.
- We would want to do that without returning a hard-coded URL.
  - We create `org.springframework.hateoas.Resource` object of type `User`. We can then add multiple links (`org.springframework.hateoas.Link`) to it.
  - A link is built using a `org.springframework.hateoas.mvc.ControllerLinkBuilder` . The builder is provided with the `Class` and the `Method` to be used for meta-data. In this case `getAllUsers`

```java
@RestController
public class UserController
{
   @Autowired
   private UserService service;
   
   @GetMapping(path="/user")
   public Collection<User> getAllUsers()
   {
      return service.findAll();
   }
   
   @GetMapping(path="/user/{id}")
   public Resource<User> getUserById(@PathVariable int id) throws NoSuchMethodException, SecurityException
   {
      // @PathVariable annotation says -- Convert the {id} segment to parameter id
      User user = service.findById(id); 
      if (user == null)
         throw new DukeResourceNotFoundException(String.format("User with ID=%d not found.", id));
      
      /**
       * HATEOAS -- Give metadata related to a resource
       * For eg: The link having all users shall be http://<host>:<port>/user
       * 
       * We do not want to hard code links. We retrieve the link attached to the controller function.
       * The controller link is built using ControllerLinkBuilder
       * 
       * Tutorial way of creating:
       * 
       * Collection<User> listUser = ControllerLinkBuilder.methodOn(getClass()).getAllUsers();
       * Link link = ControllerLinkBuilder.linkTo(listUser).withRel("all-users");
       * 
       * The above does not hard code the method name as string. However, we are invoking the function instead of 
       * pointing to it (Method object)??
       */
      Resource<User> resource = new Resource<User>(user);
      Link link = ControllerLinkBuilder.linkTo(getClass(), getClass().getMethod("getAllUsers")).withRel("all-users");
      resource.add(link);     
      
      return resource;
   }
} 
```



# Internationalisation (I18n) or Globalisation (G11n)

Internationalisation / Globalisation is providing messages in a language that preferred by the user (typically the browser). In HTTP the preference is set by the client using `Accept-Language` header. A locale is a combination of language code + an optional two letter country code. (Eg: en-US, en-GB etc)

In order the accomplish this the server would require

- A locale resolver -- A object that can resolve the locale based on the request. If none specified, it shall fall back on the default locale.
- A resource bundle -- A properties file (key=value), one for each locale (language). The properties filename should be of the format `<basename>_<locale>.properties`. Eg: `messages_fr.properties`

```java
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
     SessionLocaleResolver localeResolver = new SessionLocaleResolver();
     localeResolver.setDefaultLocale(Locale.US);
     return localeResolver;
   }
   
    /**
     * Message source easy way
     * -----------------------
     * Instead of the below code just add 'spring.messages.basename=messages' in application.properties
     */
  
//   Message source hard way
//   -----------------------  
//   @Bean("messageSource")
//   public ResourceBundleMessageSource getResourceBundleMessageSource ()
//   {
//     ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//     messageSource.setBasename("messages");
//     return messageSource;
//   }
}
```

Now, we inject a `org.springframework.context.MessageSource` into each `RestController` as follows

```java
@RestController
public class HelloController
{
  @Autowired
  private MessageSource messageSource; 
  
  @GetMapping(path = "/hello")
  public String sayHello()
  {
    return "Hello";
  }

  @GetMapping(path = "/hello-bean")
  public HelloBean sayHelloBean()
  {
    return new HelloBean("Hello from bean");
  }
  
  /**
   * Locale the hard way
   * -------------------
   * The locale is derived from 'Accept-Language' header and 'required=false' makes the header optional.
   * If the header is not present, the default Locale configured must be used.
   * The {@code RestfulWebserviceApplication.getLocaleResolver} configures default Locale. 
   * 
   * @param locale The language_Country code provided by client (Eg: en_US)
   * @return
   */
  @GetMapping(path = "/hello-g11n-hard")
  public String sayHelloG11nHard(@RequestHeader(name="Accept-Language", required=false) Locale locale)
  {
    return messageSource.getMessage("my.greeting", null, locale) + Util.LINE_SEP;
  }
  
  /**
   * Locale the easy way
   * -------------------
   * The locale is derived from 'Accept-Language' header and 'required=false' makes the header optional.
   * If the header is not present, the default Locale configured must be used.
   * The {@code RestfulWebserviceApplication.getLocaleResolver} configures default Locale. 
   * 
   * @return
   */
  @GetMapping(path = "/hello-g11n")
  public String sayHelloG11n()
  {
    return messageSource.getMessage("my.greeting", null, LocaleContextHolder.getLocale()) + Util.LINE_SEP;
  }   
}
```

# Content Negotiation

Content negotiation deals with providing response to different mime type. The client provides it's MIME type preference using `Accept` header. If the sever cannot adhere to this request it responds with `406 Not Acceptable` response code.

## Add XML response capability

By default, the response from the `@RestController` is converted to JSON. To add additional support for XML, just add the following dependency. Now, requests to `/user/1` will automatically work with header `Accept: application/xml` as well.

```xml
    <dependency>
      <groupId>com.fasterxml.jackson.dataformat</groupId>
      <artifactId>jackson-dataformat-xml</artifactId>
    </dependency>
```

>Adding the above dependency could be risky because browser prefers application/xml over application/json. However, we might prefer seeing json response in the browser as well.

# Swagger

Swagger documents the **service contract** -- This is analogous to providing help/usage for a command. A swagger UI (accessible from browser) 

Begin with creating a Swagger bean as follows. The class enables Swagger with the `api` method that adds meta-data about the API provider.

```java
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
  /**
   * API provider contact information
   */
  public static final Contact CAFEDUKE_CONTACT = new Contact("CafeDuke", "http://github.com/cafeduke",  "sliverline1981@gmail.com");  
  
  /**
   * API meta info
   */
  private static final ApiInfo CAFEDUKE_API_INFO = new ApiInfo(
      "CafeDuke API Documentation", "CafeDuke API Description", "1.0", "urn:tos",
      CAFEDUKE_CONTACT,
      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
      new ArrayList<>());

  /**
   * The MIME types that the application and consume and produce.
   */
  private static final Set<String> PRODUCES_AND_CONSUMES = Set.of("application/json", "application/xml");
  
  /**
   * @return A Swagger bean.
   */
  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(CAFEDUKE_API_INFO)
      .produces(PRODUCES_AND_CONSUMES);
  }  
}
```

## Swagger Links

By default, the Swagger can be accessed as follows 

- GUI: http://localhost:8080/swagger-ui/index.html
- JSON API docs : http://localhost:8080/v2/api-docs

## Model Metadata

More metadata can be added to Model using `io.swagger.annotations` package. Below, we see the `@ApiModel` and `@ApiModelPropery` annotations added to Model.

```java
import javax.validation.constraints.*;
import io.swagger.annotations.*;

@ApiModel(description = "The user object")
public class User
{
   private Integer id;
   
   @Size(min=2, message="Name should have atleast 2 characters")
   private String name;
   
   @ApiModelProperty (notes="Birth should be in the past")
   @Past
   private Date dob;
   
   protected User()
   {
      
   }
   ...
   ...
}     
```

# Monitoring APIs 

HAL (Hypertext Application Language) is a format that provides a **consistent way to hyperlink between resources of an API**. Adopting HAL will make API/documentation **explorable** and **discoverable**.

```xml
    <!--  Monitoring -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.data</groupId>
      <artifactId>spring-data-rest-hal-browser</artifactId>
    </dependency>
```

## Monitoring Links

- Acutator : http://localhost:8080/actuator
- HAL Browser: http://localhost:8080/browser/index.html

> An *actuator* is a component of a machine that  is responsible for controlling a mechanism or system. Eg: An actuator controls a valve.

Add the following property to `application.properties` to expose more links via the actuator.

```proper
management.endpoints.web.exposure.include=*
```

Open the HAL browser and type `/actuator` in the `Explorer` and click `Go` . This will provide the various **related resources**. The raw JSON for the same can be seen by http://localhost:8080/actuator

# Filtering

We might want to restrict a few fields of the Model bean from being sent to the client. Consider the model bean `FilterBean` below. The fields annotated with `@JsonIgnore` will not be sent to the client.

## Static Filtering

Below is a POJO with attributes to be filtered are annotated with `@JsonIgnore`

```java
/**
 * This class is used demonstrate the filtering capabilities.
 */
public class StaticFilterBean
{
  private String name = null;
  
  private String city = null;
  
  @JsonIgnore
  private int age = 0;
  
  @JsonIgnore
  private int salary = 0;

  public StaticFilterBean(String name, String city, int age, int salary)
  {
    super();
    this.name = name;
    this.city = city;
    this.age = age;
    this.salary = salary;
  }  
  // Getters and setters
}
```

The `FilterBeanController` is a `RestController` providing a mapping for `/demo/filter/static` . Upon requesting the URI, we find that sensitive data such as age and salary are **not** exposed to the client in JSON response.

```java
package com.github.cafeduke.learn.rest.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterBeanController
{
  @GetMapping(path="/demo/filter/static")
  public List<StaticFilterBean> demoStaticFiltering ()
  {
    StaticFilterBean beanA = new StaticFilterBean("Raghu.Nandan", "Bangalore", 38, 150);
    StaticFilterBean beanB = new StaticFilterBean("Pavithra.Rajagopal", "Bangalore", 36, 70);    
    return Arrays.asList(beanA, beanB);
  }
}
```

## Dynamic Filtering

In case of dynamic filtering the POJO is annotated with `@JsonFilter(<name of the filter>)`. Please **note** that the name of the filter shall be used in the controller.

```java
/**
 * This class is used demonstrate the filtering capabilities.
 */
@JsonFilter("DynamicFilter")
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

```

The controller below filters `age` and `salary` for URI `/demo/filter/dynamic/v1` and filters only `salary` for URI `/demo/filter/dynamic/v2`

```java
@RestController
public class FilterController
{ 
  /**
   * <pre>
   * The version could either "v1" or "v2".
   *  v1 -- Classified info
   *  v2 -- Liberal info
   * </pre>
   * 
   * @param version "v1" or "v2"
   * @return MappingJacksonValue -- A wrapper for the beans.
   */
  @GetMapping(path="/demo/filter/dynamic/{version}")
  public MappingJacksonValue demoDynamicFilteringV1(@PathVariable String version)
  {
    DynamicFilterBean beanA = new DynamicFilterBean("Raghu.Nandan", "Bangalore", 38, 150);
    DynamicFilterBean beanB = new DynamicFilterBean("Pavithra.Rajagopal", "Bangalore", 36, 70);

    // A holder of POJOs before further mapping instruction is passed.
    MappingJacksonValue mapping = new MappingJacksonValue(Arrays.asList(beanA, beanB));
    
    FilterProvider filterProvider = null;
    if (version.equals("v1"))
    {
      // Create a filter
      SimpleBeanPropertyFilter filterClassified = SimpleBeanPropertyFilter.filterOutAllExcept("name", "city");
      
      // Add filter to Filter Provider
      filterProvider = new SimpleFilterProvider().addFilter("DynamicFilter", filterClassified);
    }
    else 
    {
      // Create a filter
      SimpleBeanPropertyFilter filterLiberal    = SimpleBeanPropertyFilter.filterOutAllExcept("name", "city", "age");
      
      // Add filter to Filter Provider
      filterProvider = new SimpleFilterProvider().addFilter("DynamicFilter", filterLiberal);
    }
    
    mapping.setFilters(filterProvider);
    return mapping;
  }
}

```



# Versioning



# Entity

Ensure the following dependencies are part of pom.xml

```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
```

Convert the `User.java` model POJO to an Entity using `@Entity` annotation. Add the `@Id` annotation for the primary key and let it be auto generated using `@GeneratedValue`

```java
@Entity
@ApiModel(description = "The user object")
public class User
{
   @Id
   @GeneratedValue
   private Integer id;
 	 ...
   ...
}    
```

Add the following to `application.properties`

```properties
# Properties to enable localhost:8080/h2-console
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.show.sql=true
spring.h2.console.enabled=true
```

Access h2-console http://localhost:8080/h2-console

- Use the JDBC URL `jdbc:h2:mem:testdb`
- Username: sa
- Password:



