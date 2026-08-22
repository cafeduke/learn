package io.github.cafeduke.dukecart.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.cafeduke.dukecart.dao.AddressRepository;
import io.github.cafeduke.dukecart.dao.CustomerRepository;
import io.github.cafeduke.dukecart.dao.OrderItemRepository;
import io.github.cafeduke.dukecart.dao.PurchaseOrderRepository;
import io.github.cafeduke.dukecart.dto.PurchaseDTO;
import io.github.cafeduke.dukecart.dto.PurchaseResult;
import io.github.cafeduke.dukecart.entity.Address;
import io.github.cafeduke.dukecart.entity.Customer;
import io.github.cafeduke.dukecart.entity.PurchaseOrder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Note
 * ----
 *  - Spring's component scanning mechanism looks for classes annotated with @Component, @Service, @Repository, or @Controller 
 * to register them as beans in the application context. Only then Spring can instantiate these classes. So, the annotation
 * is palced on the class not the interface.
 * 
 *  - @RequiredArgsConstructor performs DI on final instance variables
 */
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService
{
    // Dependency Injection -- The injected object should be marked final to make it immutable.
    // ----------------------------------------------------------------------------------------
    
    private final CustomerRepository customerRepository;    
    
    private final AddressRepository addressRepository;
    
    private final PurchaseOrderRepository purchaseOrderRepository;
    
    private final OrderItemRepository itemRepository;
    
    @Transactional
    @Override
    public PurchaseResult placeOrder(PurchaseDTO purchase)
    {
        // Retrieve the purchase-order from the DTO
        PurchaseOrder purchaseOrder = purchase.getPurchaseOrder();
        
        // Save customer, addresses (shipping & billing), orderTrackingNumber -- update purachaseOrder
        final Customer savedCustomer = customerRepository.save(purchase.getCustomer());
        final Address savedBillingAddress = addressRepository.save(purchase.getBillingAddress());
        final Address savedShippingAddress = addressRepository.save(purchase.getShippingAddress());
        final String orderTrackingNumber = generateOrderTrackingNumber ();        
        purchaseOrder.setCustomer(savedCustomer);
        purchaseOrder.setBillingAddress(savedBillingAddress);
        purchaseOrder.setShippingAddress(savedShippingAddress);
        purchaseOrder.setOrderTrackingNumber(orderTrackingNumber);
        
        // Save purchaseOrder
        final PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        
        // Update items with savedPurchaseOrder
        purchase.getOrderItems().forEach((orderItem) ->
        {
            orderItem.setPurchaseOrder(savedPurchaseOrder);
            itemRepository.save(orderItem);
        });
        
        // return a PurchaseResult with order-tracking-number
        return new PurchaseResult(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber()
    {
        // UUID : Universally Unique ID
        return UUID.randomUUID().toString();
    }

}
