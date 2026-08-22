package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;

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
 * The persistent class for the state database table.
 */
@Entity
@Table(name = "state")
@Getter
@Setter
public class State implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    // bi-directional many-to-one association to Country
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public State()
    {
    }
}
