package com.github.ulrikewerner.backend.entities;

import java.util.ArrayList;

public record Card(
        String id,
        String name,
        NflLogoAcronym logo,
        ArrayList<CardAttribute> attributes
) {
}
