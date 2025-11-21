package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cafeduke.dukecart.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>
{

}
