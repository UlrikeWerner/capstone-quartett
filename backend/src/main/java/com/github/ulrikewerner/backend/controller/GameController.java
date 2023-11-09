package com.github.ulrikewerner.backend.controller;

import com.github.ulrikewerner.backend.dto.GameStateDTO;
import com.github.ulrikewerner.backend.dto.OpenGameDTO;
import com.github.ulrikewerner.backend.dto.PlayerTurnInputDTO;
import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.exception.*;
import com.github.ulrikewerner.backend.interfaces.GameTurn;
import com.github.ulrikewerner.backend.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping()
    public ResponseEntity<List<OpenGameDTO>> getOpenGames() {
        return ResponseEntity.ok(gameService.getOpenGames().stream().map(OpenGameDTO::new).toList());
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
            throws GameNotFoundException, CategoryNotFoundException, NotYourTurnException, GameIsOverException {
        return ResponseEntity.ok(gameService.getPlayerTurnResult(gameId, category.toString()));
    }

    @GetMapping("/{gameId}/opponentTurn")
    public ResponseEntity<GameTurn> getOpponentTurnResult(@PathVariable String gameId)
            throws GameNotFoundException, CategoryNotFoundException, NotOpponentTurnException, GameIsOverException {
        return ResponseEntity.ok(gameService.getOpponentTurnResult(gameId));
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok().build();
    }
}