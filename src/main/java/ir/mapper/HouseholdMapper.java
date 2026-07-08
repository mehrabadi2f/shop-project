package ir.mapper;

import ir.dto.HouseholdInput;
import ir.model.Household;
import org.springframework.stereotype.Component;


@Component
public class HouseholdMapper {

    public Household toEntity(HouseholdInput input) {
        Household h = new Household();
        h.setNationalId(input.getNationalId());
        h.setHouseholdSize(input.getHouseholdSize());
        return h;
    }
}
