package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;
    private UUID userId;
    private BigDecimal amount;
    private String merchant;
    private LocalDateTime timestamp;
    private String status; // Guardaremos o enum como String

    protected TransactionEntity() {}

    public TransactionEntity(UUID id, UUID userId, BigDecimal amount, String merchant, LocalDateTime timestamp, String status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public String getMerchant() { return merchant; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
}