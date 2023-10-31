package com.github.ulrikewerner.backend.entities;

public record CardAttribute(
        String name,
        int value,
        boolean isDecimal,
        boolean isBiggerBetter
) {
}
