package io.github.cafeduke.dukecart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.cafeduke.dukecart.entity.Category;
import io.github.cafeduke.dukecart.entity.Country;
import io.github.cafeduke.dukecart.entity.Product;
import io.github.cafeduke.dukecart.entity.State;

@Configuration
@EnableWebSecurity
public class DukeCartRestConfig implements RepositoryRestConfigurer, WebMvcConfigurer
{
  @SuppressWarnings("unused")
  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry registry)
  {
    HttpMethod targetHttpMethod[] = { HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };
    Class<?> targetEntityClass[] = { Product.class, Category.class, State.class, Country.class };

    // Expose Id for ALL targetEntityClass
    config.exposeIdsFor(targetEntityClass);

    // Disable ALL targetHttpMethod for ALL targetEntityClass
    for (Class<?> currEntity : targetEntityClass)
    {
      config.getExposureConfiguration()
        .forDomainType(currEntity)
        .withItemExposure((metadata, httpMethods) -> httpMethods.disable(targetHttpMethod))
        .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(targetHttpMethod));
    }

    // Configure CORS Mapping for all Data REST endpoints
    addCorsMappings(registry);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry)
  {
    registry.addMapping("/dukecart/**")
      .allowedOrigins("http://localhost:8080")
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*")
      .allowCredentials(true);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
  {
    httpSecurity
      // Enable cors
      .cors(Customizer.withDefaults())

      // Disable CSRF (often required for API-only apps)
      // .csrf(csrf -> csrf.disable())

      // Authorize requests
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/dukecart/**")
        .permitAll()
        .requestMatchers("/dukecart/checkout/purchase")
        .hasAnyRole("admin")
        .anyRequest()
        .permitAll())

      // Configure OAuth2 Resource Server
      .oauth2ResourceServer(server -> server
        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return httpSecurity.build();
  }

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults()
  {
    // Set the default prefix to an empty string
    return new GrantedAuthorityDefaults("");
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter()
  {
    // Define the JSON path to roles.
    // The path should use bracket/dot notation for JSON navigation.
    String jsonPath = "$.resource_access.angular-dukecart-client.roles";

    // No prefix needed
    String prefix = "";

    DukeJwtGrantedAuthoritiesConverter authoritiesConverter = new DukeJwtGrantedAuthoritiesConverter(jsonPath, prefix);

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
    return converter;
  }
}
