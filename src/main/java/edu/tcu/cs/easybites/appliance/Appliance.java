package edu.tcu.cs.easybites.appliance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Appliance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applianceId;

    private String name;

    public Appliance() {
    }

    public Integer getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(Integer applianceId) {
        this.applianceId = applianceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
