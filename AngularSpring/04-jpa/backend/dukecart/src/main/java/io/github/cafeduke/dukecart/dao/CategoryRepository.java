package io.github.cafeduke.dukecart.dao;

import org.springframework.data.repository.CrudRepository;
import io.github.cafeduke.dukecart.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>
{

}
