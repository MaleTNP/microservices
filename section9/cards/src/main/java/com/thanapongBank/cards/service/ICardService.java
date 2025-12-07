package com.thanapongBank.cards.service;

import com.thanapongBank.cards.dto.CardsDto;
import com.thanapongBank.cards.dto.ResponseDto;
import lombok.NoArgsConstructor;

public interface ICardService {

    void createCard(String mobileNumber);

    CardsDto fetchCard(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean deleteCard(String mobileNumber);
}
