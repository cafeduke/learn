package io.github.cafeduke.dukecart.dto;

import java.util.List;

import io.github.cafeduke.dukecart.entity.Address;
import io.github.cafeduke.dukecart.entity.Customer;
import io.github.cafeduke.dukecart.entity.OrderItem;
import io.github.cafeduke.dukecart.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;

/**
 * A data transfer object (DTO) that holds the entire purchase information submitted by front-end.
 */
@Getter
@Setter
public class PurchaseDTO
{
    private Customer customer;
    private Address shippingAddress, billingAddress;
    private PurchaseOrder purchaseOrder;
    private List<OrderItem> orderItems;
}
