package br.edu.infnet.leonardomuniz.streamingapi.streaming.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tracks")
public class TrackEntity {

    @Id
    private UUID id;
    private String title;
    private String artist;

    protected TrackEntity() {}

    public TrackEntity(UUID id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
}