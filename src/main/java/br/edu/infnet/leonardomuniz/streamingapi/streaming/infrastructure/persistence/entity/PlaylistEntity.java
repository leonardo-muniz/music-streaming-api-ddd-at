package br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlists")
public class PlaylistEntity {

    @Id
    private UUID id;
    private UUID ownerId;
    private String name;

    // Relacionamento Unidirecional: A Playlist conhece as Tracks, mas a Track não precisa conhecer a Playlist no banco.
    // CascadeType.ALL garante que ao salvar a playlist, as músicas sejam salvas juntas.
    // orphanRemoval = true garante que ao remover uma música da lista do domínio, ela seja deletada do banco.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "playlist_id")
    private List<TrackEntity> tracks = new ArrayList<>();

    protected PlaylistEntity() {}

    public PlaylistEntity(UUID id, UUID ownerId, String name, List<TrackEntity> tracks) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        if (tracks != null) {
            this.tracks.addAll(tracks);
        }
    }

    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public List<TrackEntity> getTracks() { return tracks; }
}