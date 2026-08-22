package io.github.cafeduke.dukecart.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
 * The persistent class for the country database table.
 */
@Entity
@Table(name = "country")
@Getter
@Setter
public class Country implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String code;

    @Column
    private String name;

    // bi-directional many-to-one association to State
    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<State> states;

    public Country()
    {
    }

    public State addState(State state)
    {
        getStates().add(state);
        state.setCountry(this);
        return state;
    }

    public State removeState(State state)
    {
        getStates().remove(state);
        state.setCountry(null);
        return state;
    }
}
