package br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID userId,
        BigDecimal amount,
        String merchant,
        String status,
        LocalDateTime timestamp
) {}