package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Game;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BaseGameStateDTO {
    private final Map<String, Integer> score;
    public BaseGameStateDTO(Game game) {
        score = new HashMap<>();
        score.put("player", game.getPlayerCards().size());
        score.put("opponent", game.getOpponentCards().size());
    }
}
