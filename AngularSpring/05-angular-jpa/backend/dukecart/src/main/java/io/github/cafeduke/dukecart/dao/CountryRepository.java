package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import io.github.cafeduke.dukecart.entity.Country;

@CrossOrigin("http://localhost:8080")
public interface CountryRepository extends JpaRepository<Country, Integer>
{

}
