package com.jerin.cards.repository;

import com.jerin.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards,Long> {
     Optional<Cards> findBymobileNumber(String MobileNumber);

     Optional<Cards> findByCardNumber(String cardNumber);
}
