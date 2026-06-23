package br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.service;

import java.util.UUID;

// Interface que protege o domínio de Streaming
// de conhecer o contexto de Billing
public interface ActiveSubscriptionChecker {
    boolean check(UUID userId);
}