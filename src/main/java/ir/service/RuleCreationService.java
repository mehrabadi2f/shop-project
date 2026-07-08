package ir.service;

import ir.dto.ConditionRequest;
import ir.dto.RuleCreationRequest;
import ir.model.Rule;
import ir.model.RuleCondition;
import ir.repository.RuleConditionRepository;
import ir.repository.RuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RuleCreationService {

    private final RuleRepository ruleRepository;
    private final RuleConditionRepository conditionRepository;

    public RuleCreationService(RuleRepository ruleRepository,
                               RuleConditionRepository conditionRepository) {
        this.ruleRepository = ruleRepository;
        this.conditionRepository = conditionRepository;
    }

    @Transactional
    public Rule createRule(RuleCreationRequest request) {
        // ۱. ساخت قانون
        Rule rule = new Rule();
        rule.setName(request.getName());
        rule.setAllowedActions(request.getAllowedActions());
        rule.setWithdrawPercentage(request.getWithdrawPercentage());
        rule.setTransferPercentage(request.getTransferPercentage());
        rule.setActive(true);

        rule = ruleRepository.save(rule);

        // ۲. ذخیره شرایط
        for (ConditionRequest condReq : request.getConditions()) {
            RuleCondition condition = new RuleCondition();
            condition.setRule(rule);
            condition.setFieldName(condReq.getFieldName());
            condition.setOperator(condReq.getOperator());
            condition.setValue(condReq.getValue());
            condition.setOperator(condReq.getOperator());
            condition.setValue(condReq.getValue());

            // *** ذخیره نوع رابطه ***
            // اگر ادمین چیزی نفرستاد، پیش‌فرض AND در نظر گرفته میشه
            if (condReq.getRelationType() != null && !condReq.getRelationType().isEmpty()) {
               condition.setRelationType(RuleCondition.RelationType.valueOf(condReq.getRelationType()));
            } else {
                condition.setRelationType(RuleCondition.RelationType.AND);
            }

            conditionRepository.save(condition);
        }

        return rule;
    }
}
