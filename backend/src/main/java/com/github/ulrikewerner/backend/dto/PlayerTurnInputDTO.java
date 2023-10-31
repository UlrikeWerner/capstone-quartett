package com.github.ulrikewerner.backend.dto;

public record PlayerTurnInputDTO(
        String category
) {
    @Override
    public String toString(){
        return category;
    }
}
