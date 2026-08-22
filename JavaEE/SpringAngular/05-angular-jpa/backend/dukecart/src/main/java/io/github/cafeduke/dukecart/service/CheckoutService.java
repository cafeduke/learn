package io.github.cafeduke.dukecart.service;

import io.github.cafeduke.dukecart.dto.PurchaseDTO;
import io.github.cafeduke.dukecart.dto.PurchaseResult;

public interface CheckoutService
{
    PurchaseResult placeOrder (PurchaseDTO purchase);
}
