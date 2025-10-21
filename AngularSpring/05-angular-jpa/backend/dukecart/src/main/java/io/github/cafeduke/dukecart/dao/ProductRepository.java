package io.github.cafeduke.dukecart.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.github.cafeduke.dukecart.entity.Product;

// The CrossOrigin tells server running JPA to accept requests from localhost:8080
@CrossOrigin("http://localhost:8080")
public interface ProductRepository extends JpaRepository<Product, Long>
{
     Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable page);
     
     Page<Product> findByNameContaining(@Param("name") String name, Pageable page);
}
