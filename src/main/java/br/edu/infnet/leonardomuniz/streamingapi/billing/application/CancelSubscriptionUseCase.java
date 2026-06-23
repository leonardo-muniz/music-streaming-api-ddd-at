package br.edu.infnet.leonardomuniz.streamingapi.billing.application;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelSubscriptionUseCase {

    private final UserRepository userRepository;

    public CancelSubscriptionUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        user.cancelSubscription();

        userRepository.save(user);
    }
}