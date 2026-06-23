package br.edu.infnet.leonardomuniz.streamingapi.streaming.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.repository.PlaylistRepository;

@Service
public class GetPlaylistUseCase {

    private final PlaylistRepository playlistRepository;

    public GetPlaylistUseCase(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public Playlist execute(UUID id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada."));
    }
}