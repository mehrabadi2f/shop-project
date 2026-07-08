package ir.model;


import ir.service.model.TransferStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class TransferRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private Instant createdAt;

    // getters & setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFromAccountId() { return fromAccountId; }

    public void setFromAccountId(String fromAccountId) { this.fromAccountId = fromAccountId; }

    public String getToAccountId() { return toAccountId; }

    public void setToAccountId(String toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransferStatus getStatus() { return status; }

    public void setStatus(TransferStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
