package br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Playlist {
    private final UUID id;
    private final UUID ownerId;
    private final String name;
    private final List<Track> tracks;

    // Construtor 1: Para reconstruir do banco de dados H2 (USA OS PARÂMETROS)
    public Playlist(UUID id, UUID ownerId, String name, List<Track> tracks) {
        this.id = id != null ? id : UUID.randomUUID();
        this.ownerId = ownerId;
        this.name = name;
        this.tracks = tracks != null ? new ArrayList<>(tracks) : new ArrayList<>();
    }

    // Construtor 2: Para criar uma NOVA playlist via API
    public Playlist(UUID ownerId, String name) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
        this.name = name;
        this.tracks = new ArrayList<>();
    }

    public void addTrack(Track track) {
        if (tracks.contains(track)) {
            throw new IllegalArgumentException("A música já está na playlist.");
        }
        this.tracks.add(track);
    }

    public void removeTrack(UUID trackId) {
        boolean removed = this.tracks.removeIf(track -> track.id().equals(trackId));
        if (!removed) {
            throw new IllegalArgumentException("A música não foi encontrada nesta playlist.");
        }
    }

    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getName() { return name; }
    public List<Track> getTracks() { return new ArrayList<>(tracks); }
}