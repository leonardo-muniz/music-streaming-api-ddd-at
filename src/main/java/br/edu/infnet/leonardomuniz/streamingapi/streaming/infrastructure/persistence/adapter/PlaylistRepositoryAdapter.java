package br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Track;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.repository.PlaylistRepository;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.entity.PlaylistEntity;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.entity.TrackEntity;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.repository.SpringDataPlaylistRepository;

@Component
public class PlaylistRepositoryAdapter implements PlaylistRepository {

    private final SpringDataPlaylistRepository springDataRepository;

    public PlaylistRepositoryAdapter(SpringDataPlaylistRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public void save(Playlist playlist) {
        // 1. Converte as Tracks do Domínio para TrackEntity (JPA)
        List<TrackEntity> trackEntities = playlist.getTracks().stream()
                .map(track -> new TrackEntity(track.id(), track.title(), track.artist()))
                .toList();

        // 2. Converte a Playlist do Domínio para PlaylistEntity (JPA)
        PlaylistEntity entity = new PlaylistEntity(
                playlist.getId(),
                playlist.getOwnerId(),
                playlist.getName(),
                trackEntities
        );

        // 3. Salva no banco H2
        springDataRepository.save(entity);
    }

    @Override
    public Optional<Playlist> findById(UUID id) {
        return springDataRepository.findById(id).map(entity -> {
            // 1. Converte as TrackEntities (JPA) de volta para o Value Object Track (Domínio)
            List<Track> domainTracks = entity.getTracks().stream()
                    .map(t -> new Track(t.getId(), t.getTitle(), t.getArtist()))
                    .toList();

            // 2. Reconstrói o Aggregate Root Playlist (Domínio) mantendo os dados imutáveis
            return new Playlist(
                    entity.getId(),
                    entity.getOwnerId(),
                    entity.getName(),
                    domainTracks
            );
        });
    }
}