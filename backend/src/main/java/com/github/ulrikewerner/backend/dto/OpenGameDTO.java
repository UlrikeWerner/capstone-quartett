package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Game;
import lombok.Getter;

@Getter
public class OpenGameDTO {
    private final String gameId;
    private final String title;

    public OpenGameDTO(Game game){
        gameId = game.getId();
        title = game.getTitle();
    }
}
