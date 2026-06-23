package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final UUID id;
    private final UUID userId;
    private final BigDecimal amount;
    private final String merchant;
    private final LocalDateTime timestamp;
    private TransactionStatus status;

    public enum TransactionStatus { PENDING, AUTHORIZED, DENIED }

    public Transaction(UUID userId, BigDecimal amount, String merchant) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
        this.status = TransactionStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    // Construtor para reconstruir a entidade a partir do banco de dados (Adapter)
    public Transaction(UUID id, UUID userId, BigDecimal amount, String merchant, LocalDateTime timestamp, TransactionStatus status) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.merchant = merchant;
        this.timestamp = timestamp;
        this.status = status;
    }

    public void authorize() {
        this.status = TransactionStatus.AUTHORIZED;
    }

    public void deny() {
        this.status = TransactionStatus.DENIED;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public String getMerchant() { return merchant; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public TransactionStatus getStatus() { return status; }
}