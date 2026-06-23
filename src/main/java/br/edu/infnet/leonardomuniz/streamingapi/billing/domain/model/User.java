package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String name;
    private final String email;
    private Subscription activeSubscription;
    private final CardInfo creditCard;

    // Construtor para reconstruir a entidade a partir do banco de dados (Adapter)
    public User(UUID id, String name, String email, CardInfo creditCard) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.creditCard = creditCard;
    }

    // Construtor para criar um NOVO usuário
    public User(String name, String email, CardInfo creditCard) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.creditCard = creditCard;
    }

    public void subscribeTo(String planName) {
        if (hasActiveSubscription()) {
            throw new  IllegalStateException("O usuário já possui um plano ativo.");
        }
        if (!this.creditCard.isValid()) {
            throw new IllegalStateException("Cartão de crédito inativo ou inválido.");
        }
        this.activeSubscription = new Subscription(planName);
    }

    public boolean hasActiveSubscription() {
        return this.activeSubscription != null && this.activeSubscription.isActive();
    }

    public void cancelSubscription() {
        if (this.activeSubscription == null || !this.activeSubscription.isActive()) {
            throw new IllegalStateException("O usuário não possui uma assinatura ativa para cancelar.");
        }
        this.activeSubscription.cancel();
    }

    public void restoreSubscription(Subscription subscription) {
        this.activeSubscription = subscription;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public CardInfo getCreditCard() { return creditCard; }
    public Subscription getActiveSubscription() { return activeSubscription; }
}