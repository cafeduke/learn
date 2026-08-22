package io.github.cafeduke.dukecart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cafeduke.dukecart.entity.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>
{

}
