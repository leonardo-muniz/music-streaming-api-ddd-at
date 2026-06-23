package br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.leonardomuniz.streamingapi.billing.infrastructure.persistence.entity.UserEntity;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {}