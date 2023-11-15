package edu.tcu.cs.easybites.allergen;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Allergen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer allergenId;

    private String name;

    public Allergen() {
    }

    public Integer getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Integer allergenId) {
        this.allergenId = allergenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
