package br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
        UUID userId,
        BigDecimal amount,
        String merchant
) {}