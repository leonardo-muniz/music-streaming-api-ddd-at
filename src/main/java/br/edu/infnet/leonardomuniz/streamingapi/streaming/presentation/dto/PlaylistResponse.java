package br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto;

import java.util.List;
import java.util.UUID;

public record PlaylistResponse(UUID id, String name, List<TrackResponse> tracks) {}