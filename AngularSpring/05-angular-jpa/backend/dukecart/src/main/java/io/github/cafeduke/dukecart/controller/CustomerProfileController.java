package io.github.cafeduke.dukecart.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cafeduke.dukecart.dto.CustomerProfileDTO;
import io.github.cafeduke.dukecart.entity.Customer;
import io.github.cafeduke.dukecart.service.CustomerProfileService;
import lombok.RequiredArgsConstructor;

/**
 * Note:
 * - @RequiredArgsConstructor performs DI on final instance variables
 * - @CrossOrigin tells server running JPA to accept requests from localhost:8080
 */
@RestController
@RequestMapping("/dukecart/customer-profile")
@RequiredArgsConstructor
public class CustomerProfileController
{
  // Dependency Injection -- The injected object should be marked final to make it immutable.
  // ----------------------------------------------------------------------------------------
  public final CustomerProfileService customerProfileService;
  
  @PostMapping("/create")
  public Customer createCustomerProfile (@RequestBody CustomerProfileDTO customerProfileDTO)
  {
    assertAuthentication ();
    return customerProfileService.createCustomerProfile(customerProfileDTO);
  }
  
  @GetMapping("/read/{username}")
  public CustomerProfileDTO readCustomerProfile (@PathVariable String username)
  {
    assertAuthentication ();
    return customerProfileService.readCustomerProfile(username);
  }
  
  @PostMapping("/update")
  public Customer updateCustomerProfile (@RequestBody CustomerProfileDTO customerProfileDTO)
  {
    assertAuthentication ();
    return customerProfileService.updateCustomerProfile(customerProfileDTO);
  }
  
  @DeleteMapping("/delete/{username}")
  public Customer deleteCustomerProfile (@PathVariable String username)
  {
    assertAuthentication ();
    return customerProfileService.deleteCustomerProfile(username);
  }    
  
  /**
   * Throw excpetion if the user is NOT authenticated.
   */
  private void assertAuthentication ()
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated())
      throw new IllegalStateException ("User not authenticated");
  }
}
