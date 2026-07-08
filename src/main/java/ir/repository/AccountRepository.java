package ir.repository;

import ir.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByUserId(Long userid);
    Optional<Account> findByUserId(Long userid);
    Optional<Account> findByUserUsername(String username);
}

