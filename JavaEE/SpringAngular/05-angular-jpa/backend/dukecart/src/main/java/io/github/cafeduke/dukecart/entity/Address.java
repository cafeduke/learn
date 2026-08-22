package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the address database table.
 */
@Entity
@Table(name = "address")
@Getter
@Setter
public class Address implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    public Address()
    {
    }
}
