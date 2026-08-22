package io.github.cafeduke.dukecart.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import io.github.cafeduke.dukecart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
     Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable page);
     
     Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
}
