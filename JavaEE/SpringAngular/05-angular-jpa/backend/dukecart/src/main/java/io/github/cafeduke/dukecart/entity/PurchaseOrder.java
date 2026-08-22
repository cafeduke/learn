package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the purchase_order database table.
 */
@Entity
@Table(name = "purchase_order")
@Getter
@Setter
public class PurchaseOrder implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;
    
    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column
    private String status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "total_quantity")
    private int totalQuantity;
    
    // bi-directional many-to-one association to Customer
    // Owning side of relationship (having the foreign key)
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;    

    // bi-directional many-to-one association to Address
    // Owning side of relationship (having the foreign key)
    @OneToOne
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    // bi-directional many-to-one association to Address
    // Owning side of relationship (having the foreign key)
    @OneToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;


    // bi-directional many-to-one association to Item    
    // Derived side of relationship
    @OneToMany(mappedBy = "purchaseOrder")
    private List<OrderItem> orderItems;

    public PurchaseOrder()
    {
    }

}
