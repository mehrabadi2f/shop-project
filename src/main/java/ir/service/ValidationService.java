package ir.service;
import ir.validation.NationalIdValidator;
import ir.dto.HouseholdInput;


import org.springframework.stereotype.Service;


import ir.repository.HouseholdRepository;

@Service
public class ValidationService {

    private static final long MAX_POPULATION = 90_000_000;

    private final NationalIdValidator nationalIdValidator;
    private final HouseholdRepository householdRepository;

    public ValidationService(
            NationalIdValidator nationalIdValidator,
            HouseholdRepository householdRepository) {
        this.nationalIdValidator = nationalIdValidator;
        this.householdRepository = householdRepository;
    }

    /** بررسی صحت رکورد ورودی */
    public boolean validateRecord(HouseholdInput input) {
        if (!nationalIdValidator.isValid(input.getNationalId()))
            return false;

        return input.getHouseholdSize() != null && input.getHouseholdSize() > 0;
    }

    /** بررسی اینکه جمع اعضا از ۹۰ میلیون بیشتر نشود */
    public void validatePopulationLimit(long batchMembers) {
        long current = householdRepository.getTotalHouseholdMembers();

        if (current + batchMembers > MAX_POPULATION) {
            throw new IllegalStateException("Population exceeds 90 million limit");
        }
    }
}

