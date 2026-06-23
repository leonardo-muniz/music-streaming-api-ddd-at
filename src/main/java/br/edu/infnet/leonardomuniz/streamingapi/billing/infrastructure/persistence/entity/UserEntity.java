package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;
    private String name;
    private String email;

    // Para simplificar no H2, vamos armazenar os dados do Value Object CardInfo diretamente na tabela do usuário
    private String cardNumber;
    private boolean cardActive;

    private String planName;
    private boolean planActive;

    private LocalDateTime planCreatedAt;

    // Construtor vazio exigido pelo JPA
    protected UserEntity() {}

    public UserEntity(UUID id, String name, String email, String cardNumber, boolean cardActive, String planName, boolean planActive, LocalDateTime planCreatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cardNumber = cardNumber;
        this.cardActive = cardActive;
        this.planName = planName;
        this.planActive = planActive;
        this.planCreatedAt = planCreatedAt;
    }

    // Getters e Setters necessários para o JPA
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCardNumber() { return cardNumber; }
    public boolean isCardActive() { return cardActive; }
    public String getPlanName() { return planName; }
    public boolean isPlanActive() { return planActive; }
    public LocalDateTime getPlanCreatedAt() { return planCreatedAt; }
}