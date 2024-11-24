package com.jerin.cards.service.impl;

import com.jerin.cards.constants.CardsConstants;
import com.jerin.cards.dto.CardsDto;
import com.jerin.cards.entity.Cards;
import com.jerin.cards.exception.CardAlreadyExistsException;
import com.jerin.cards.exception.ResourceNotFoundException;
import com.jerin.cards.mapper.CardsMapper;
import com.jerin.cards.repository.CardsRepository;
import com.jerin.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards= cardsRepository.findBymobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber : "+mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));

    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        newCard.setCreatedAt(LocalDateTime.now());
        newCard.setCreatedBy("Anonymous");
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findBymobileNumber( mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Cards","Mobile Number",mobileNumber)
        );

        return CardsMapper.mapToCardsDto(cards,new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDTO) {

        Cards cards = cardsRepository.findByCardNumber( cardsDTO.getCardNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Cards","Card Number",cardsDTO.getCardNumber())
        );
        CardsMapper.mapToCards(cards,cardsDTO);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findBymobileNumber( mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Cards","Mobile Number",mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
