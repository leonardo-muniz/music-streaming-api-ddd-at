package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.adapter;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.UserRepository;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.service.ActiveSubscriptionChecker;

@Component
public class BillingSubscriptionAdapter implements ActiveSubscriptionChecker {

    // A infraestrutura PODE enxergar outros módulos para fins de integração
    private final UserRepository userRepository;

    public BillingSubscriptionAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean check(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent() && user.get().hasActiveSubscription();
    }
}