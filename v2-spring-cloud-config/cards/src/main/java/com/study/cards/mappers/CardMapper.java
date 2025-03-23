package com.study.cards.mappers;

import com.study.cards.dtos.CardDto;
import com.study.cards.entities.Card;

public class CardMapper {

    public static CardDto mapToCardsDto(Card card, CardDto cardDto) {

        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setAmountUsed(card.getAmountUsed());

        return cardDto;
    }

    public static Card mapToCards(CardDto cardDto, Card card) {

        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setAmountUsed(cardDto.getAmountUsed());

        return card;
    }
}
