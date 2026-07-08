package ir.repository;

import ir.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {

    @Modifying
    @Query(
            value = """
                INSERT INTO household (national_id, household_size)
                VALUES (:nationalId, :householdSize)
                ON CONFLICT (national_id) DO NOTHING
            """,
            nativeQuery = true
    )
    void insertIgnoreDuplicate(
            @Param("nationalId") String nationalId,
            @Param("householdSize") Integer householdSize
    );

    // --------------------------
    // 2) Sum of all household_size
    // --------------------------
    @Query(
            value = "SELECT COALESCE(SUM(h.household_size), 0) FROM household h",
            nativeQuery = true
    )
    long getTotalHouseholdMembers();
}
