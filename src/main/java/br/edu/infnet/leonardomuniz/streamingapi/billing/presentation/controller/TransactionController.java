package br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.leonardomuniz.streamingapi.billing.application.GetTransactionUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.billing.application.ProcessTransactionUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;
import br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.dto.TransactionRequest;
import br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.dto.TransactionResponse;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final ProcessTransactionUseCase processTransactionUseCase;
    private final GetTransactionUseCase getTransactionUseCase;

    public TransactionController(ProcessTransactionUseCase processTransactionUseCase, 
                                 GetTransactionUseCase getTransactionUseCase) {
        this.processTransactionUseCase = processTransactionUseCase;
        this.getTransactionUseCase = getTransactionUseCase;
    }

    @PostMapping("/process")
    public ResponseEntity<Void> processTransaction(@RequestBody TransactionRequest request) {
        processTransactionUseCase.execute(
                request.userId(),
                request.amount(),
                request.merchant()
        );

        // Se o Use Case não lançar exceções, a transação foi aprovada e salva.
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable UUID transactionId) {
        Transaction transaction = getTransactionUseCase.execute(transactionId);
        
        TransactionResponse response = new TransactionResponse(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getMerchant(),
                transaction.getStatus().name(),
                transaction.getTimestamp()
        );
        
        return ResponseEntity.ok(response);
    }
}