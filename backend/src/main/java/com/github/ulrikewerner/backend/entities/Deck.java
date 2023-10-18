package com.github.ulrikewerner.backend.entities;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class Deck {
    private List<Card> cards;
}
