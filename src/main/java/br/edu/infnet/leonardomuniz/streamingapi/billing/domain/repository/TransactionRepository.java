package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;

public interface TransactionRepository {
    
    void save(Transaction transaction);

    int countTransactionsByUserInPeriod(UUID userId, 
                                        LocalDateTime start, 
                                        LocalDateTime end);
    
    int countSimilarTransactionsByUserInPeriod(UUID userId, 
                                               BigDecimal amount, 
                                               String merchant, 
                                               LocalDateTime start, 
                                               LocalDateTime end);

    Optional<Transaction> findById(UUID id);
}