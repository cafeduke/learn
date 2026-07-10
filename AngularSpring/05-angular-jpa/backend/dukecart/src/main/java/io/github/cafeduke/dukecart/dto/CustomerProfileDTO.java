package io.github.cafeduke.dukecart.dto;

import java.util.List;

import io.github.cafeduke.dukecart.entity.Address;
import io.github.cafeduke.dukecart.entity.Customer;
import lombok.Getter;
import lombok.Setter;

/**
 * A data transfer object (DTO) that holds the entire purchase information submitted by front-end.
 */
@Getter
@Setter
public class CustomerProfileDTO
{
  private Customer customer;
  private List<Address> addresses;
}
