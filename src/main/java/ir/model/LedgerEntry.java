package ir.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountId;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "transfer_record_id")
    private TransferRecord transferRecord;

    private Instant createdAt;

    // getters & setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransferRecord getTransferRecord() { return transferRecord; }

    public void setTransferRecord(TransferRecord transferRecord) { this.transferRecord = transferRecord; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
