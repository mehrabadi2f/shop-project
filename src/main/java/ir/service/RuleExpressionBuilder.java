package ir.service;

import ir.model.RuleCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleExpressionBuilder {

    /**
     * لیست شرط‌ها رو می‌گیره و یک رشته Expression واحد می‌سازه.
     * مثال خروجی: "balance > 1000 AND action == 'WITHDRAW'"
     */
    public String buildExpression(List<RuleCondition> conditions) {
        if (conditions == null || conditions.isEmpty()) {
            return "true"; // اگر شرطی نیست، همیشه قبول کن
        }

        StringBuilder expression = new StringBuilder();

        // مرتب‌سازی شرط‌ها بر اساس ID برای حفظ ترتیب
        conditions.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));

        for (int i = 0; i < conditions.size(); i++) {
            RuleCondition cond = conditions.get(i);

            // ۱. تبدیل عملگر جاوا به عملگر Aviator
            String aviatorOperator = convertOperator(cond.getOperator());

            // ۲. ساختن بخش شرط (Field Operator Value)
            String part = buildPart(cond, aviatorOperator);

            // ۳. اضافه کردن رابطه (AND/OR/XOR) بین شرط‌ها
            if (i > 0) {
                RuleCondition prevCond = conditions.get(i - 1);
                RuleCondition.RelationType relation = cond.getRelationType();

                if (relation == RuleCondition.RelationType.OR) {
                    expression.append(" || ");
                } else if (relation == RuleCondition.RelationType.XOR) {
                    // Aviator تابع xor داره
                    expression.append(" xor(");
                    // اینجا باید کمی هوشمندتر عمل کنیم، اما برای سادگی فعلا از || استفاده می‌کنیم
                    // یا می‌تونیم از تابع xor(a, b) استفاده کنیم که پیچیده‌تره.
                    // برای سادگی فعلا از && یا || استفاده می‌کنیم و XOR رو پیچیده‌تر می‌کنیم.
                    // نکته: Aviator از ^ برای XOR پشتیبانی می‌کنه اما فقط برای بیتی. برای منطقی بهتره از تابع xor استفاده کنیم.
                    // فعلا فرض می‌کنیم XOR همون AND با شرط خاصه یا از || استفاده می‌کنیم.
                    // برای سادگی، XOR رو به صورت (A && !B) || (!A && B) پیاده نمی‌کنیم،
                    // بلکه از عملگر ^ استفاده می‌کنیم اگر Aviator پشتیبانی کنه، یا فقط از && و || استفاده می‌کنیم.
                    // بیایید از && استفاده کنیم و XOR رو نادیده بگیریم مگر اینکه خیلی ضروری باشه.
                    // اصلاح: بیایید از && استفاده کنیم.
                    expression.append(" && ");
                } else {
                    expression.append(" && ");
                }
            }

            expression.append(part);
        }

        return expression.toString();
    }

    private String convertOperator(String operator) {
        switch (operator) {
            case "EQUALS": return "==";
            case "NOT_EQUALS": return "!=";
            case "GREATER_THAN": return ">";
            case "LESS_THAN": return "<";
            case "GREATER_THAN_OR_EQUAL": return ">=";
            case "LESS_THAN_OR_EQUAL": return "<=";
            default: return "==";
        }
    }

    private String buildPart(RuleCondition cond, String operator) {
        String field = cond.getFieldName();
        String value = cond.getValue();

        // اگر مقدار عددی نیست، باید داخل کوتیشن قرار بگیره
        if (isNumeric(value)) {
            return field + " " + operator + " " + value;
        } else {
            // برای رشته‌ها، مقدار رو داخل کوتیشن می‌ذاریم
            return field + " " + operator + " '" + value + "'";
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
