package io.github.cafeduke.dukecart.dao;

import org.springframework.data.repository.CrudRepository;

import io.github.cafeduke.dukecart.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long>
{

}
