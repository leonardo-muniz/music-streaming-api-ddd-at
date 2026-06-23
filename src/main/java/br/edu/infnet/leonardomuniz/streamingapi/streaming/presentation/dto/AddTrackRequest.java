package br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto;

import java.util.UUID;

public record AddTrackRequest(
        UUID trackId,
        String title,
        String artist
) {}