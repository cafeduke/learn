package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cafeduke.dukecart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{

}
