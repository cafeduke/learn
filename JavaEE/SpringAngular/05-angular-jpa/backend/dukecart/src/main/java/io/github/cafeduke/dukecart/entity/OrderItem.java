package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the item database table.
 */
@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "product_id")
    private long productId;

    @Column
    private int quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    // bi-directional many-to-one association to PurchaseOrder
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;
}
