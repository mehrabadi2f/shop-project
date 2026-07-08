package ir.service;

import ir.model.Rule;
import ir.repository.RuleRepository;
import org.springframework.stereotype.Service;
import ir.model.UserContext;
import ir.dto.TransactionRequest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleEngineService {

private  RuleEvaluationService ruleEvaluationService;
    private final RuleRepository ruleRepository;

    public RuleEngineService(RuleRepository ruleRepository , RuleEvaluationService ruleEvaluationService) {
        this.ruleRepository = ruleRepository;
        this.ruleEvaluationService = ruleEvaluationService;
    }

    /**
     * بررسی می‌کند که آیا تراکنش مجاز است یا خیر
     */
    public boolean checkPermission(UserContext user, TransactionRequest transaction) {
        List<Rule> activeRules = ruleRepository.findByActiveTrue();

        Map<String, Object> context = new HashMap<>();
        context.put("nationalCode", user.getNationalCode());
        context.put("membershipDate", user.getMembershipDate());
        context.put("subsidyYear", user.getSubsidyYear());
        context.put("subsidyMonth", user.getSubsidyMonth());
        context.put("balance", user.getBalance());

        context.put("action", transaction.getAction());
        context.put("amount", transaction.getAmount());
        context.put("targetAccount", transaction.getTargetAccount());

        for (Rule rule : activeRules) {
            try {
                boolean isAllowed = ruleEvaluationService.evaluateRule(rule, context);
                //Object result = AviatorEvaluator.execute(rule.getExpression(), context);

                if (!Boolean.TRUE.equals(isAllowed)) {
                    continue;
                }

                if (rule.getAllowedActions() == null || !rule.getAllowedActions().contains(transaction.getAction())) {
                    continue;
                }

                double allowedAmount = 0;
                if ("WITHDRAW".equals(transaction.getAction())) {
                    allowedAmount = user.getBalance() * (rule.getWithdrawPercentage() / 100.0);
                } else if ("TRANSFER".equals(transaction.getAction())) {
                    allowedAmount = user.getBalance() * (rule.getTransferPercentage() / 100.0);
                } else {
                    continue;
                }

                if (transaction.getAmount() > allowedAmount) {
                    continue;
                }

                return true;

            } catch (Exception e) {
                System.err.println("Error evaluating rule: " + rule.getName() + " - " + e.getMessage());
            }
        }

        return false;
    }
}
