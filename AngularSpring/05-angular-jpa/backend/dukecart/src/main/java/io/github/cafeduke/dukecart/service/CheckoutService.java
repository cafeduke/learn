package io.github.cafeduke.dukecart.service;

import io.github.cafeduke.dukecart.dto.PurchaseDTO;
import io.github.cafeduke.dukecart.dto.PurchaseResultDTO;

public interface CheckoutService
{
    PurchaseResultDTO placeOrder (PurchaseDTO purchase);
}
