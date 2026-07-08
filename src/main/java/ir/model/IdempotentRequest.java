package ir.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class IdempotentRequest {

    @Id
    private String id;

    private Instant createdAt;

    public IdempotentRequest() {
    }

    public IdempotentRequest(String id, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    // getters & setters
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }

    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}

