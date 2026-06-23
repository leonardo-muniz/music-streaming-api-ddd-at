package br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.leonardomuniz.streamingapi.streaming.application.AddTrackToPlaylistUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.application.GetPlaylistUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.application.RemoveTrackFromPlaylistUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Playlist;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.domain.model.Track;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto.AddTrackRequest;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto.PlaylistResponse;
import br.edu.infnet.leonardomuniz.streamingapi.streaming.presentation.dto.TrackResponse;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final AddTrackToPlaylistUseCase addTrackToPlaylistUseCase;
    private final GetPlaylistUseCase getPlaylistUseCase;
    private final RemoveTrackFromPlaylistUseCase removeTrackFromPlaylistUseCase;
    
    public PlaylistController(AddTrackToPlaylistUseCase addTrackToPlaylistUseCase, 
                              GetPlaylistUseCase getPlaylistUseCase,
                              RemoveTrackFromPlaylistUseCase removeTrackFromPlaylistUseCase) {
        this.addTrackToPlaylistUseCase = addTrackToPlaylistUseCase;
        this.getPlaylistUseCase = getPlaylistUseCase;
        this.removeTrackFromPlaylistUseCase = removeTrackFromPlaylistUseCase;
    }

    @PostMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> addTrack(
            @PathVariable UUID playlistId,
            @RequestHeader("X-User-Id") UUID userId, // Simulando o ID do usuário logado
            @RequestBody AddTrackRequest request) {

        Track track = new Track(request.trackId(), request.title(), request.artist());

        addTrackToPlaylistUseCase.execute(userId, playlistId, track);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistResponse> getPlaylist(@PathVariable UUID playlistId) {
        Playlist playlist = getPlaylistUseCase.execute(playlistId);

        // Converte as Tracks do Domínio para DTOs
        List<TrackResponse> trackResponses = playlist.getTracks().stream()
                .map(track -> new TrackResponse(track.id(), track.title(), track.artist()))
                .toList();

        // Converte a Playlist do Domínio para DTO
        PlaylistResponse response = new PlaylistResponse(
                playlist.getId(), 
                playlist.getName(), 
                trackResponses
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Void> removeTrack(
            @PathVariable UUID playlistId,
            @PathVariable UUID trackId) {

        removeTrackFromPlaylistUseCase.execute(playlistId, trackId);

        // 204 No Content é o status correto para um DELETE bem-sucedido
        return ResponseEntity.noContent().build();
    }
}