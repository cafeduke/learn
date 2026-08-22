package io.github.cafeduke.dukecart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * The result of purchase is getting a order tracking number to track the purchase-order
 * 
 * Note
 * ----
 *  - The @RequiredArgsConstructor annotation generates constructor arguments that sets all final fields 
 */
@Getter
@Setter
@RequiredArgsConstructor
public class PurchaseResult
{
    private final String orderTrackingNumber;
}
