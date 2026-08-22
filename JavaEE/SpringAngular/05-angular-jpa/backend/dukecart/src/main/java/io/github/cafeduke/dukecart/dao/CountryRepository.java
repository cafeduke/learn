package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cafeduke.dukecart.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>
{

}
