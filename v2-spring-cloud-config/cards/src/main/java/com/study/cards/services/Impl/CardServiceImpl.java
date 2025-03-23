package com.study.cards.services.Impl;

import com.study.cards.constants.CardsConstants;
import com.study.cards.dtos.CardDto;
import com.study.cards.entities.Card;
import com.study.cards.exceptions.CardAlreadyExistsException;
import com.study.cards.exceptions.ResourceNotFoundException;
import com.study.cards.mappers.CardMapper;
import com.study.cards.repositories.CardRepository;
import com.study.cards.services.ICardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Card> optionalCard= cardRepository.findByMobileNumber(mobileNumber);

        if(optionalCard.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
        }

        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {

        Card newCard = new Card();

        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);

        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return newCard;
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {

        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        return CardMapper.mapToCardsDto(card, new CardDto());
    }

    @Override
    public boolean updateCard(CardDto cardsDto) {

        Card card = cardRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));

        CardMapper.mapToCards(cardsDto, card);

        cardRepository.save(card);

        return  true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {

        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        cardRepository.deleteById(card.getCardId());

        return true;
    }
}
