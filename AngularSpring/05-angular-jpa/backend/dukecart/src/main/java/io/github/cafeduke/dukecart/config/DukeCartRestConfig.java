package io.github.cafeduke.dukecart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import io.github.cafeduke.dukecart.entity.Category;
import io.github.cafeduke.dukecart.entity.Country;
import io.github.cafeduke.dukecart.entity.Product;
import io.github.cafeduke.dukecart.entity.State;

@Configuration
public class DukeCartRestConfig implements RepositoryRestConfigurer
{
    @SuppressWarnings("unused")
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors)
    {
        HttpMethod targetHttpMethod[] = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE};
        Class<?> targetEntityClass[] = {Product.class, Category.class, State.class, Country.class};
        
        config.exposeIdsFor(targetEntityClass);
        
        // Disable ALL targetHttpMethod for ALL targetEntityClass
        for (Class<?> currEntity: targetEntityClass)
        {
            config.getExposureConfiguration()
                .forDomainType(currEntity)
                .withItemExposure((metadata, httpMethods) ->  httpMethods.disable(targetHttpMethod))
                .withCollectionExposure((metadata, httpMethods) ->  httpMethods.disable(targetHttpMethod));
        }
        
    }
}
