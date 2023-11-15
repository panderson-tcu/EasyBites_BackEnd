package edu.tcu.cs.easybites.protein;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Protein implements Serializable {
    private String proteinName;

    @Id
    private Integer proteinId;

    public Protein() {

    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String proteinName) {
        this.proteinName = proteinName;
    }

    public Integer getProteinId() {
        return proteinId;
    }

    public void setProteinId(Integer proteinId) {
        this.proteinId = proteinId;
    }
}
