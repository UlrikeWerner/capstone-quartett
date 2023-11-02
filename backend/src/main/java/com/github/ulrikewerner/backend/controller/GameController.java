package com.github.ulrikewerner.backend.controller;

import com.github.ulrikewerner.backend.dto.GameStateDTO;
import com.github.ulrikewerner.backend.dto.PlayerTurnInputDTO;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.GameTurn;
import com.github.ulrikewerner.backend.exception.CategoryNotFoundException;
import com.github.ulrikewerner.backend.exception.GameNotFoundException;
import com.github.ulrikewerner.backend.exception.NotOpponentTurnException;
import com.github.ulrikewerner.backend.exception.NotYourTurnException;
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

    @PutMapping("/{gameId}")
    public ResponseEntity<GameTurn> getTurnResult(@PathVariable String gameId, @RequestBody PlayerTurnInputDTO category)
            throws GameNotFoundException, CategoryNotFoundException, NotYourTurnException {
        return ResponseEntity.ok(gameService.getPlayerTurnResult(gameId, category.toString()));
    }

    @GetMapping("/{gameId}/opponentTurn")
    public ResponseEntity<GameTurn> getOpponentTurnResult(@PathVariable String gameId)
            throws GameNotFoundException, CategoryNotFoundException, NotOpponentTurnException {
        return ResponseEntity.ok(gameService.getOpponentTurnResult(gameId));
    }
}