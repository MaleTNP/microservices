package com.thanapongBank.cards.service.impl;

import com.thanapongBank.cards.constants.CardsConstant;
import com.thanapongBank.cards.dto.CardsDto;
import com.thanapongBank.cards.entity.Cards;
import com.thanapongBank.cards.exception.CardAlreadyExistException;
import com.thanapongBank.cards.exception.ResourceNotFoundException;
import com.thanapongBank.cards.mapper.CardsMapper;
import com.thanapongBank.cards.repository.CardsRepository;
import com.thanapongBank.cards.service.ICardService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistException("Mobile Number already exist: " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    public Cards createNewCard(String mobileNumber) {
        Cards newCards = new Cards();
        Long cardNumber = 100000000000L + new Random().nextInt(900000000);
        newCards.setMobileNumber(mobileNumber);
        newCards.setCardNumber(cardNumber.toString());
        newCards.setCardType(CardsConstant.CREDIT_CARD);
        newCards.setTotalLimit(CardsConstant.NEW_CARD_LIMIT);
        newCards.setAmountUsed(0);
        newCards.setAvailableAmount(CardsConstant.NEW_CARD_LIMIT);
        newCards.setCreatedAt(LocalDateTime.now());
        newCards.setCreatedBy("Anonymous");
        return newCards;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards= cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber())
        );
        cardsRepository.save(CardsMapper.mapToCards(cardsDto, cards));
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
