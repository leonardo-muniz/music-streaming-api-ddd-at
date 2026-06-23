package br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.entity.PlaylistEntity;

@Repository
public interface SpringDataPlaylistRepository extends JpaRepository<PlaylistEntity, UUID> {}