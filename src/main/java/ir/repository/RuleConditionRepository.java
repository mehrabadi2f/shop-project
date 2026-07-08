package ir.repository;


import ir.model.Rule;
import ir.model.RuleCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// RuleRepository.java
public interface RuleConditionRepository extends JpaRepository<RuleCondition, Long> {
    // پیدا کردن قوانین فعال
    List<Rule> findByActiveTrue();
    List<Rule> findByActiveFalse();
    List<RuleCondition>findByRuleId(Long ruleId);
}

