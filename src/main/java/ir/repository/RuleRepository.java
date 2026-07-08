package ir.repository;


import ir.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// RuleRepository.java
public interface RuleRepository extends JpaRepository<Rule, Long> {
    // پیدا کردن قوانین فعال
    List<Rule> findByActiveTrue();
}

