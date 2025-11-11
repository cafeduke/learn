package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.github.cafeduke.dukecart.entity.Address;

//The CrossOrigin tells server running JPA to accept requests from localhost:8080
@CrossOrigin("http://localhost:8080")
public interface AddressRepository extends JpaRepository<Address, Long>
{

}
