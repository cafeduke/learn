package io.github.cafeduke.dukecart.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * - @RequiredArgsConstructor performs DI on final instance variables
 * - @CrossOrigin tells server running JPA to accept requests from localhost:8080
 */
@RestController
@RequestMapping("/dukecart/checkout")
@RequiredArgsConstructor
public class CheckoutController
{
    // Dependency Injection -- The injected object should be marked final to make it immutable.
    // ----------------------------------------------------------------------------------------
    public final CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResult placeOrder(@RequestBody PurchaseDTO purchase)
    {
        System.out.println("[RBSESHAD CheckoutController] Inside getRoles");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // Extract role names (e.g., "ROLE_ADMIN", "ROLE_USER")
            Collection<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
            System.out.println("[RBSESHAD CheckoutController] User roles: " + roles);
        }  
        
        return checkoutService.placeOrder(purchase);
    }

}
