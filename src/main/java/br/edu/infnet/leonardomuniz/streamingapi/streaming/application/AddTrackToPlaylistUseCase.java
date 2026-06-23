package br.edu.infnet.leonardomuniz.streamingapi.streaming.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Track;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.repository.PlaylistRepository;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.service.ActiveSubscriptionChecker;

@Service
public class AddTrackToPlaylistUseCase {

    private final PlaylistRepository playlistRepository;
    private final ActiveSubscriptionChecker subscriptionChecker;

    public AddTrackToPlaylistUseCase(PlaylistRepository playlistRepository,
                                     ActiveSubscriptionChecker subscriptionChecker) {
        this.playlistRepository = playlistRepository;
        this.subscriptionChecker = subscriptionChecker;
    }

    @Transactional
    public void execute(UUID userId, UUID playlistId, Track track) {
        // 1. Valida se o usuário tem assinatura ativa (Context Mapping via ACL)
        if (!subscriptionChecker.check(userId)) {
            throw new IllegalStateException("O usuário não possui um plano ativo para modificar playlists.");
        }

        // 2. Busca o Aggregate (Playlist)
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new IllegalArgumentException("Playlist não encontrada."));

        // Garante que apenas o dono pode adicionar músicas
        if (!playlist.getOwnerId().equals(userId)) {
            throw new IllegalStateException("Apenas o dono da playlist pode adicionar músicas.");
        }

        // 3. Aplica a regra de negócio do Aggregate
        playlist.addTrack(track);

        playlistRepository.save(playlist);
    }
}