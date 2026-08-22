package com.github.cafeduke.learn.rest.demo.filter;

import java.util.*;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilterController
{
  @GetMapping(path="/demo/filter/static")
  public List<StaticFilterBean> demoStaticFiltering ()
  {
    StaticFilterBean beanA = new StaticFilterBean("Raghu.Nandan", "Bangalore", 38, 150);
    StaticFilterBean beanB = new StaticFilterBean("Pavithra.Rajagopal", "Bangalore", 36, 70);    
    return Arrays.asList(beanA, beanB);
  }
  
  /**
   * <pre>
   * The version could either "v1" or "v2".
   *  v1 -- Classified info
   *  v2 -- Liberal info
   * </pre>
   * 
   * @param version "v1" or "v2"
   * @return
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
      filterProvider = new SimpleFilterProvider().addFilter("DemoDynamicFilter", filterClassified);
    }
    else 
    {
      // Create a filter
      SimpleBeanPropertyFilter filterLiberal    = SimpleBeanPropertyFilter.filterOutAllExcept("name", "city", "age");
      
      // Add filter to Filter Provider
      filterProvider = new SimpleFilterProvider().addFilter("DemoDynamicFilter", filterLiberal);
    }
    
    mapping.setFilters(filterProvider);
    return mapping;
  }
}
