package ir.service;

import com.googlecode.aviator.AviatorEvaluator;
import ir.model.Rule;
import ir.model.RuleCondition;
import ir.repository.RuleConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleEvaluationService {

    private final RuleConditionRepository conditionRepository;
    private final RuleExpressionBuilder expressionBuilder;

    public RuleEvaluationService(RuleConditionRepository conditionRepository,
                                 RuleExpressionBuilder expressionBuilder) {
        this.conditionRepository = conditionRepository;
        this.expressionBuilder = expressionBuilder;
    }

    /**
     * بررسی می‌کنه که آیا یک تراکنش با قوانین مشخص شده مطابقت داره یا نه
     */
    public boolean evaluateRule(Rule rule, Map<String, Object> context) {
        // ۱. گرفتن تمام شرط‌های مربوط به این قانون
        List<RuleCondition> conditions = conditionRepository.findByRuleId(rule.getId());

        if (conditions.isEmpty()) {
            return true; // اگر شرطی نیست، قبول کن
        }

        // ۲. تبدیل شرط‌ها به یک رشته Expression
        String expression = expressionBuilder.buildExpression(conditions);

        // ۳. اجرای Expression با استفاده از Aviator
        try {
            Object result = AviatorEvaluator.execute(expression, context);
            return (Boolean) result;
        } catch (Exception e) {
            // اگر خطایی در اجرای Expression رخ داد (مثلا فیلد ناموجوده)
            e.printStackTrace();
            return false;
        }
    }
}
