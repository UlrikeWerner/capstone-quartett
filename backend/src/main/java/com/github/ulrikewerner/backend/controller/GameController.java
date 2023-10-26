package com.github.ulrikewerner.backend.controller;

import com.github.ulrikewerner.backend.dto.GameStateDTO;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping()
    public ResponseEntity<String> startNewGame() {
        return ResponseEntity.ok(gameService.startNewGame().getId());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameStateDTO> getGameState(@PathVariable String gameId) {
        Optional<Game> optionalGame = gameService.getGameById(gameId);
        return optionalGame
                .map(game -> ResponseEntity.ok(new GameStateDTO(game)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
