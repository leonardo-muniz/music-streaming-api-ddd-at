package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model;

public record CardInfo(String cardNumber, boolean isActive) {
    public boolean isValid() {
        return isActive && cardNumber != null && !cardNumber.isBlank();
    }
}