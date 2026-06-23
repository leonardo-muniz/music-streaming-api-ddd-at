package br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.repository;

import java.util.Optional;
import java.util.UUID;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;

public interface PlaylistRepository {
    Optional<Playlist> findById(UUID id);
    void save(Playlist playlist);
}