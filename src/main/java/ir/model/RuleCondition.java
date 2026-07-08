package ir.model;

import jakarta.persistence.*;

//import javax.persistence.*;

@Entity
@Table(name = "rule_conditions")
public class RuleCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ارتباط با قانون اصلی
    @ManyToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule rule;

    // نام فیلد در جدول کاربر (مثلا: "nationalCode", "subsidyYear")
    private String fieldName;

    // عملگر مقایسه: EQUAL, GREATER_THAN, LESS_THAN, BETWEEN, CONTAINS
    private String operator;

    // مقدار شرط (برای BETWEEN دو مقدار نیاز داریم، پس اینجا رشته ذخیره می‌کنیم و پارس می‌کنیم)
    // مثال برای BETWEEN: "1000,2000"
    private String value;

private boolean active=true;
    // *** تغییر جدید: نوع رابطه با شرط قبلی ***
    // مقادیر مجاز: "AND", "OR", "XOR"
    // پیش‌فرض: "AND"
    @Enumerated(EnumType.STRING)
    private RelationType relationType = RelationType.AND;

    // Enum برای انواع رابطه
    public enum RelationType {
        AND, OR, XOR
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Rule getRule() { return rule; }
    public void setRule(Rule rule) { this.rule = rule; }
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public RelationType getRelationType(){return this.relationType;}
    public void setRelationType(RelationType relationType){this.relationType = relationType;}
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

}
