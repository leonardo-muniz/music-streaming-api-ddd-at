package br.edu.infnet.leonardomuniz.streamingapi.streaming.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.repository.PlaylistRepository;

@Service
public class RemoveTrackFromPlaylistUseCase {

    private final PlaylistRepository playlistRepository;

    public RemoveTrackFromPlaylistUseCase(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public void execute(UUID playlistId, UUID trackId) {
        // 1. Busca a playlist no banco
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada."));

        // 2. Remove a música (a própria entidade valida se a música existe nela)
        playlist.removeTrack(trackId);

        // 3. Salva a playlist atualizada (O JPA vai deletar a música
        // da tabela 'tracks' automaticamente devido ao orphanRemoval=true)
        playlistRepository.save(playlist);
    }
}