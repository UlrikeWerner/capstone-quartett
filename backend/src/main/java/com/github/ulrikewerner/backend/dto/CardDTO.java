package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CardDTO {
    private final String name;
    private Map<String, String> attributes;

    public CardDTO(Card card) {
        name = card.name();
        setAttributes(card.attributes());
    }

    private void setAttributes(ArrayList<CardAttribute> attributeList) {
        this.attributes = new HashMap<>();
        for(CardAttribute attribute : attributeList){
            if(attribute.isDecimal()){
                attributes.put(attribute.name(), Double.toString(attribute.value() / 100.0));
            } else {
                attributes.put(attribute.name(), Integer.toString(attribute.value()));
            }
        }
    }
}
