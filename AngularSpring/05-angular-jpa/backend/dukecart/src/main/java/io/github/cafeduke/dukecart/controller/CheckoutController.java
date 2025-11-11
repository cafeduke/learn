package io.github.cafeduke.dukecart.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cafeduke.dukecart.dto.PurchaseDTO;
import io.github.cafeduke.dukecart.dto.PurchaseResult;
import io.github.cafeduke.dukecart.service.CheckoutService;
import lombok.RequiredArgsConstructor;

/**
 * Note:
 *  - @RequiredArgsConstructor performs DI on final instance variables
 *  - @CrossOrigin tells server running JPA to accept requests from localhost:8080
 */
@RestController
@RequestMapping("/dukecart/checkout")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8080")
public class CheckoutController
{
    // Dependency Injection -- The injected object should be marked final to make it immutable.
    // ----------------------------------------------------------------------------------------
    public final CheckoutService checkoutService;
    
    @PostMapping("/purchase")
    public PurchaseResult placeOrder (@RequestBody PurchaseDTO purchase)
    {
        return checkoutService.placeOrder(purchase);
    }
    
}
