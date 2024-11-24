package com.jerin.cards.service;

import com.jerin.cards.dto.CardsDto;

public interface ICardsService {

    public void createCard(String mobileNumber);

    public CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDTO);

    boolean deleteCard(String mobileNumber);
}
