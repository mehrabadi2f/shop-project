package ir.service;


import ir.model.Household;
import ir.repository.HouseholdJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class HouseholdBatchService {

    private final HouseholdJdbcRepository repository;

    public HouseholdBatchService(HouseholdJdbcRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveBatch(List<Household> batch) {

        repository.batchInsertIgnoreDuplicates(batch);
    }
}

