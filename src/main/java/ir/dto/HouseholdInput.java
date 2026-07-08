package ir.dto;


public class HouseholdInput {

    private String nationalId;
    private Integer householdSize;

    public HouseholdInput() {
    }

    public HouseholdInput(String nationalId, Integer householdSize) {
        this.nationalId = nationalId;
        this.householdSize = householdSize;
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