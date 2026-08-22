package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the customer database table.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    // bi-directional many-to-one association to PurchaseOrder
    @OneToMany(mappedBy = "customer")
    private List<PurchaseOrder> purchaseOrders;

    public Customer()
    {
    }

}
