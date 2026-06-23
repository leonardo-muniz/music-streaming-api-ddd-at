package br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto;

import java.util.UUID;

public record TrackResponse(UUID id, String title, String artist) {}