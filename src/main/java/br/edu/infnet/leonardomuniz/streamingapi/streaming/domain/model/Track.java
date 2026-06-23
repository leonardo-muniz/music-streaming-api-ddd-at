package br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model;

import java.util.UUID;

public record Track(UUID id, String title, String artist) {}