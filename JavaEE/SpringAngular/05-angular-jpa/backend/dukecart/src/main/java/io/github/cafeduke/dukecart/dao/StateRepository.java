package io.github.cafeduke.dukecart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import io.github.cafeduke.dukecart.entity.State;

public interface StateRepository extends JpaRepository<State, Integer>
{
    // URI: /states/search/findByCountryCode?code=<code>
    List<State> findByCountryCode(@Param("code") String code);
}
