package com.github.ulrikewerner.backend.dto;

import com.github.ulrikewerner.backend.entities.Card;
import com.github.ulrikewerner.backend.entities.CardAttribute;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class CardDTO {
    private final String name;
    private final String logo;
    private final Map<String, String> attributes = new LinkedHashMap<>();

    public CardDTO(Card card) {
        name = card.name();
        logo = card.logo();
        setAttributes(card.attributes());
    }

    private void setAttributes(ArrayList<CardAttribute> attributeList) {
        for(CardAttribute attribute : attributeList){
            if(attribute.isDecimal()){
                attributes.put(attribute.name(), Double.toString(attribute.value() / 100.0));
            } else {
                attributes.put(attribute.name(), Integer.toString(attribute.value()));
            }
        }
    }
}
