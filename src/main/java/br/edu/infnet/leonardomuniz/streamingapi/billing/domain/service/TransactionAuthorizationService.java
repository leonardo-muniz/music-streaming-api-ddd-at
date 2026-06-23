package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.service;

import java.time.LocalDateTime;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.TransactionRepository;

public class TransactionAuthorizationService {

    private final TransactionRepository transactionRepository;

    public TransactionAuthorizationService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void authorizeTransaction(User user, Transaction transaction) {
        if (user.getCreditCard().isValid()) {
            transaction.deny();
            throw new IllegalStateException("Transação negada: Cartão não ativo ou inválido.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoMinutesAgo = now.minusMinutes(2);

        int recentTransactions = transactionRepository.countTransactionsByUserInPeriod(
                user.getId(), twoMinutesAgo, now);

        if (recentTransactions >= 3) {
            transaction.deny();
            throw new IllegalStateException("Transação negada: Alta frequência em pequeno intervalo.");
        }

        int similarTransactions = transactionRepository.countSimilarTransactionsByUserInPeriod(
                user.getId(), transaction.getAmount(), transaction.getMerchant(), twoMinutesAgo, now);

        if (similarTransactions >= 2) {
            transaction.deny();
            throw new IllegalStateException("Transação negada: Transação duplicada.");
        }

        transaction.authorize();
        transactionRepository.save(transaction);
    }
}