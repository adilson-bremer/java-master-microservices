package com.study.cards.services;

import com.study.cards.dtos.CardDto;

public interface ICardService {

    void createCard(String mobileNumber);

    CardDto fetchCard(String mobileNumber);

    boolean updateCard(CardDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
