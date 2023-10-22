package com.github.ulrikewerner.backend.entities;

public record Card(
        String id,
        String team,
        float pointsPerGame
) {
}
