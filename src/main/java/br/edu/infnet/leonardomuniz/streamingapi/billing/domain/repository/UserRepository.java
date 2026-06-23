package br.edu.infnet.leonardomuniz.streamingapi.billing.domain.repository;

import java.util.Optional;
import java.util.UUID;

import br.edu.infnet.leonardomuniz.streamingapi.billing.domain.model.User;

public interface UserRepository {
    Optional<User> findById(UUID id);
    void save(User user);
}