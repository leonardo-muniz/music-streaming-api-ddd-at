package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Subscription {

    private final UUID id;
    private final String planName;
    private boolean active;
    private final LocalDateTime createdAt;

    // Construtor para reconstruir a entidade a partir do banco de dados
    public Subscription(UUID id, String planName, boolean active, LocalDateTime createdAt) {
        this.id = id != null ? id : UUID.randomUUID();
        this.planName = planName;
        this.active = active;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    // Construtor para criar uma NOVA assinatura na aplicação
    public Subscription(String planName) {
        this.id = UUID.randomUUID();
        this.planName = planName;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public void cancel() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public UUID getId() { return id; }
    public String getPlanName() { return planName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}