package ir.model;


import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rules")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // نام قانون، مثلا "قانون واریز ویژه"

    // وضعیت فعال بودن
    private boolean active = true;

    // لیست اکشن‌های مجاز (مثلا: WITHDRAW, TRANSFER)
    @ElementCollection
    private List<String> allowedActions;

    // درصد مجاز برای برداشت
    private double withdrawPercentage;

    // درصد مجاز برای کارت به کارت
    private double transferPercentage;

    // اگر می‌خوای قانون رو به یک کمپین خاص وصل کنی (اختیاری)
   @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaigns campaign;



    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { active = active; }
    public List<String> getAllowedActions() { return allowedActions; }
    public void setAllowedActions(List<String> allowedActions) { this.allowedActions = allowedActions; }
    public double getWithdrawPercentage() { return withdrawPercentage; }
    public void setWithdrawPercentage(double withdrawPercentage) { this.withdrawPercentage = withdrawPercentage; }
    public double getTransferPercentage() { return transferPercentage; }
    public void setTransferPercentage(double transferPercentage) { this.transferPercentage = transferPercentage; }
    public Campaigns getCampaign() { return campaign; }
    public void setCampaign(Campaigns campaign) { this.campaign = campaign; }
}

