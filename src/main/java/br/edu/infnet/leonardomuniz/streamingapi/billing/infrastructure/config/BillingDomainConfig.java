package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.TransactionRepository;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.service.TransactionAuthorizationService;

@Configuration
public class BillingDomainConfig {

    @Bean
    public TransactionAuthorizationService transactionAuthorizationService(TransactionRepository transactionRepository) {
        return new TransactionAuthorizationService(transactionRepository);
    }
}