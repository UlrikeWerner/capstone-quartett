package com.github.ulrikewerner.backend.entities;

public record Card(
        String id,
        String team,
        double pointsPerGame
) {
}
