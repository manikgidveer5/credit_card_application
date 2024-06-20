package com.example.Credit_Card_Application.repository;

import com.example.Credit_Card_Application.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.cardID = :cardID")
    Optional<Card> findByCardID(@Param("cardID") Long cardID);
}
