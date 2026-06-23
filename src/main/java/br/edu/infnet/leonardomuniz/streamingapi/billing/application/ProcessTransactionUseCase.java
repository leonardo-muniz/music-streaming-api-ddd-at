package br.edu.infnet.leonardomuniz.streamingapi.billing.application;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Transaction;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.UserRepository;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.service.TransactionAuthorizationService;

@Service
public class ProcessTransactionUseCase {

    private final UserRepository userRepository;
    private final TransactionAuthorizationService authorizationService;

    // A injeção de dependência vai fornecer as implementações concretas (Infraestrutura) em tempo de execução
    public ProcessTransactionUseCase(UserRepository userRepository,
                                     TransactionAuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void execute(UUID userId, BigDecimal amount, String merchant) {
        // 1. Busca o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // 2. Instancia o Aggregate de Transação
        Transaction transaction = new Transaction(user.getId(), amount, merchant);

        // 3. Delega para o Domain Service aplicar as regras antifraudes
        // Se alguma regra falhar, uma exceção será lançada e o @Transactional fará o rollback
        authorizationService.authorizeTransaction(user, transaction);
    }
}