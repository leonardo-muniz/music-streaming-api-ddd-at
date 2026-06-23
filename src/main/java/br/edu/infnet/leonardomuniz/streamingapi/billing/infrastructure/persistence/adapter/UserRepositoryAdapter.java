package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.CardInfo;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.Subscription;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;
import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository.UserRepository;
import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity.UserEntity;
import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.repository.SpringDataUserRepository;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id).map(entity -> {
            // 1. Reconstrói o Value Object do Cartão
            CardInfo cardInfo = new CardInfo(entity.getCardNumber(), entity.isCardActive());

            // 2. Reconstrói o Aggregate Root do Usuário
            User user = new User(entity.getId(), entity.getName(), entity.getEmail(), cardInfo);

            // 3. Reconstrói a Assinatura (se existir no banco)
            if (entity.getPlanName() != null) {
                // AQUI OS AVISOS SOMEM: Usando os métodos da Entidade
                Subscription subscription = new Subscription(
                        null, // ID pode ser nulo na reconstrução simplificada
                        entity.getPlanName(),
                        entity.isPlanActive(),
                        entity.getPlanCreatedAt()
                );
                user.restoreSubscription(subscription);
            }

            return user;
        });
    }

    @Override
    public void save(User user) {
        Subscription subscription = user.getActiveSubscription();

        String planName = (subscription != null) ? subscription.getPlanName() : null;
        boolean planActive = (subscription != null) && subscription.isActive();

        LocalDateTime planCreatedAt = (subscription != null) ? subscription.getCreatedAt() : null;

        UserEntity entity = new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreditCard().cardNumber(),
                user.getCreditCard().isActive(),
                planName,
                planActive,
                planCreatedAt
        );

        springDataRepository.save(entity);
    }
}