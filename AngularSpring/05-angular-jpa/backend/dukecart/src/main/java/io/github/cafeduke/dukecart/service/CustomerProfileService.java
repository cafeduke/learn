package io.github.cafeduke.dukecart.service;

import io.github.cafeduke.dukecart.dto.CustomerProfileDTO;
import io.github.cafeduke.dukecart.entity.Customer;

public interface CustomerProfileService
{
  Customer createCustomerProfile (CustomerProfileDTO profile);
  CustomerProfileDTO readCustomerProfile (String username);
  Customer updateCustomerProfile (CustomerProfileDTO profile);
  Customer deleteCustomerProfile (String username);
}
