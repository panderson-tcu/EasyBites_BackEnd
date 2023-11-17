package edu.tcu.cs.easybites.userfilter;

import edu.tcu.cs.easybites.protein.Protein;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserFilter implements Serializable {

    // will contain cost and time filter
    // many to many connection with protein
    // join table in UserFilter object here

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer filterId;

    private Integer timeLimit;

    private Double costLimit;

    @ManyToMany
    @JoinTable(
            name="protein_filter",
            joinColumns = @JoinColumn(name="user_filter_id"),
            inverseJoinColumns = @JoinColumn(name="protein_id")
    )
    private List<Protein> proteins = new ArrayList<>();

    public UserFilter() {
    }

    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Double getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(Double costLimit) {
        this.costLimit = costLimit;
    }

}
