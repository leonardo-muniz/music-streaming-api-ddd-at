package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity.TransactionEntity;

@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.userId = :userId AND t.timestamp BETWEEN :start AND :end")
    int countByUserIdAndTimestampBetween(@Param("userId") UUID userId,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(t) FROM TransactionEntity t WHERE t.userId = :userId AND t.amount = :amount AND t.merchant = :merchant AND t.timestamp BETWEEN :start AND :end")
    int countSimilarTransactions(@Param("userId") UUID userId,
                                 @Param("amount") BigDecimal amount,
                                 @Param("merchant") String merchant,
                                 @Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);
}