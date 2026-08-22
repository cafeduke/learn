package com.github.cafeduke.learn.rest;

import java.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
