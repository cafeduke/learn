package io.github.cafeduke.dukecart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import io.github.cafeduke.dukecart.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
  Optional<Customer> findByUsername(String username);
  
  @Modifying
  @Transactional
  void deleteByUsername(String username);
}
