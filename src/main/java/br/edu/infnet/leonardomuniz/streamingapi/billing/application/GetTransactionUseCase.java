package br.edu.infnet.leonardomuniz.streamingapi.billing.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.TransactionRepository;

@Service
public class GetTransactionUseCase {

    private final TransactionRepository transactionRepository;

    public GetTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction execute(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada."));
    }
}