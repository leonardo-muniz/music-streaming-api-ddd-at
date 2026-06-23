package br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.controller;

import java.util.UUID;

import br.edu.infnet.leonardomuniz.streamingapi.billing.application.CancelSubscriptionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.leonardomuniz.streamingapi.billing.application.SubscribeUserUseCase;
import br.edu.infnet.leonardomuniz.streamingapi.billing.presentation.dto.SubscribeRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final SubscribeUserUseCase subscribeUserUseCase;
    private final CancelSubscriptionUseCase cancelSubscriptionUseCase;

    public UserController(SubscribeUserUseCase subscribeUserUseCase,
                          CancelSubscriptionUseCase cancelSubscriptionUseCase) {
        this.subscribeUserUseCase = subscribeUserUseCase;
        this.cancelSubscriptionUseCase = cancelSubscriptionUseCase;
    }

    @PostMapping("/{userId}/subscribe")
    public ResponseEntity<Void> subscribe(
            @PathVariable UUID userId,
            @RequestBody SubscribeRequest request) {

        subscribeUserUseCase.execute(userId, request.planName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/cancel-subscription")
    public ResponseEntity<Void> cancelSubscription(@PathVariable UUID userId) {
        cancelSubscriptionUseCase.execute(userId);
        return ResponseEntity.ok().build();
    }
}