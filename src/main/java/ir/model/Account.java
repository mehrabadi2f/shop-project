package ir.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    private String id;

    private String ownerName;

    private BigDecimal balance;

    @Version
    private Long version;


    // چون اول User ساخته می‌شود و بعداً اکانت:
    // اینجا nullable باید true باشد (nullable را false نکن)
    @OneToOne(optional = false) // یعنی هر Account باید user داشته باشد (در سطح object)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private user user;

    public Account() {
    }

    public Account(String id, String ownerName, BigDecimal balance ,user user) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
        this.user = user;
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
