package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.NextTurnBy;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class GameStateDTO {
    private final Map<String, Integer> score;
    private final NextTurnBy nextTurnBy;
    private final CardDTO nextPlayerCard;

    public GameStateDTO(Game game) {
        score = new HashMap<>();
        score.put("player", game.getPlayerCards().size());
        score.put("opponent", game.getOpponentCards().size());
        nextTurnBy = game.isPlayerTurn() ? NextTurnBy.PLAYER : NextTurnBy.OPPONENT;
        nextPlayerCard = new CardDTO(Objects.requireNonNull(
                game.getPlayerCards()
                        .lookAtFirstCard()
                        .orElse(null)
        ));
    }
}
