package io.github.cafeduke.dukecart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.cafeduke.dukecart.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    List<Address> findByCustomerId(@Param("categoryId") Long customerId);

    @Query("SELECT a.id FROM Address a WHERE a.customer.id = :customerId")
    List<Long> findIdsByCustomerId(@Param("customerId") Long customerId);
}
