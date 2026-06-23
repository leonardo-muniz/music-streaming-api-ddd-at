package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.adapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.TransactionRepository;
import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity.TransactionEntity;
import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.repository.SpringDataTransactionRepository;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository springDataRepository;

    public TransactionRepositoryAdapter(SpringDataTransactionRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public void save(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getTimestamp(),
                transaction.getStatus().name()
        );
        springDataRepository.save(entity);
    }

    @Override
    public int countTransactionsByUserInPeriod(UUID userId, LocalDateTime start, LocalDateTime end) {
        return springDataRepository.countByUserIdAndTimestampBetween(userId, start, end);
    }

    @Override
    public int countSimilarTransactionsByUserInPeriod(UUID userId, BigDecimal amount, String merchant, LocalDateTime start, LocalDateTime end) {
        return springDataRepository.countSimilarTransactions(userId, amount, merchant, start, end);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return springDataRepository.findById(id).map(entity -> 
            // Reconstrói o objeto de Domínio a partir da Entidade do Banco (JPA)
            new Transaction(
                    entity.getId(),
                    entity.getUserId(),
                    entity.getAmount(),
                    entity.getMerchant(),
                    entity.getTimestamp(),
                    Transaction.TransactionStatus.valueOf(entity.getStatus())
            )
        );
    }
}