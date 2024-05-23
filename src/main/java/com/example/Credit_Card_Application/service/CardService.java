package com.example.Credit_Card_Application.service;

import com.example.Credit_Card_Application.exception.CardNotFoundException;
import com.example.Credit_Card_Application.exception.CardServiceException;
import com.example.Credit_Card_Application.model.Card;
import com.example.Credit_Card_Application.repository.CardHolderDetailsRepository;
import com.example.Credit_Card_Application.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardHolderDetailsRepository cardHolderDetailsRepository;

    public List<Card> getAllCards() {
        try {
            return cardRepository.findAll();
        } catch (Exception e) {
            throw new CardServiceException("Failed to fetch all cards");
        }
    }

    public List<Card> searchCards(String searchTerm) {
        try {
            List<Card> allCards = cardRepository.findAll();
            return allCards.stream()
                    .filter(card -> String.valueOf(card.getCardNumber()).contains(searchTerm) ||
                            card.getCardName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            card.getExpiryDate().contains(searchTerm) ||
                            String.valueOf(card.getCvv()).contains(searchTerm) ||
                            card.getCardHolderDetails().getCardHolderName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            card.getCardHolderDetails().getEmail().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            String.valueOf(card.getCardHolderDetails().getContactNo()).contains(searchTerm))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CardServiceException("Failed to search cards");
        }
    }

    public Optional<Card> getCardById(Long id) {
        try {
            return cardRepository.findById(id);
        } catch (Exception e) {
            throw new CardNotFoundException("Card not found with ID: " + id);
        }
    }

    public String updateCard(Card card) {
        try {
            if (card.getCardHolderDetails() != null && card.getCardHolderDetails().getCardHolderId() == 0) {
                cardHolderDetailsRepository.save(card.getCardHolderDetails());
            }
            cardRepository.save(card);
            return "Card updated successfully";
        } catch (Exception e) {
            throw new CardServiceException("Failed to update card");
        }
    }



    public String deleteCard(Long id) {
        try {
            Optional<Card> card = cardRepository.findById(id);
            if (card.isPresent()) {
                cardRepository.deleteById(id);
                return "Card deleted";
            } else {
                throw new CardNotFoundException("Card not found with ID: " + id);
            }
        } catch (CardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CardServiceException("Failed to delete card");
        }
    }

    public Card generateCardDetails() {
        Random rand = new Random();
        long cardNumber = rand.nextLong() % 900000000L + 100000000L; // Generate a 9-digit card number
        String expiryDate = String.format("%02d/%04d", rand.nextInt(12) + 1, rand.nextInt(10) + 2023); // Generate MM/YYYY format for expiry date
        int cvv = rand.nextInt(900) + 100; // Generate a 3-digit CVV
        Card cardDetails = new Card(cardNumber, "Credit Card", expiryDate, cvv);
        return cardDetails;
    }

}
