package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Game;
import com.github.ulrikewerner.backend.entities.NextTurnBy;
import lombok.Getter;

import java.util.Objects;

@Getter
public class GameStateDTO extends BaseGameStateDTO{
    private final NextTurnBy nextTurnBy;
    private final CardDTO nextPlayerCard;

    public GameStateDTO(Game game) {
        super(game);
        nextTurnBy = game.isPlayerTurn() ? NextTurnBy.PLAYER : NextTurnBy.OPPONENT;
        nextPlayerCard = new CardDTO(Objects.requireNonNull(
                game.getPlayerCards()
                        .lookAtFirstCard()
                        .orElse(null)
        ));
    }
}
