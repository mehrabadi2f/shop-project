package ir.model;

import jakarta.persistence.*;


import jakarta.persistence.*;

@Entity
@Table(
        name = "household",
        uniqueConstraints = @UniqueConstraint(columnNames = "national_id")
)
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "national_id", unique = true, nullable = false, length = 10)
    private String nationalId;

    @Column(name = "household_size", nullable = false)
    private Integer householdSize;

    public Household() {
    }

    public Long getId() {
        return id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Integer getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(Integer householdSize) {
        this.householdSize = householdSize;
    }


}

