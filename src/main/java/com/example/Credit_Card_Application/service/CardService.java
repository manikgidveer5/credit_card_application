package com.example.Credit_Card_Application.service;

import com.example.Credit_Card_Application.exception.CardNotFoundException;
import com.example.Credit_Card_Application.exception.CardServiceException;
import com.example.Credit_Card_Application.model.Card;
import com.example.Credit_Card_Application.model.CardHolderDetails;
import com.example.Credit_Card_Application.model.CardType;
import com.example.Credit_Card_Application.repository.CardHolderDetailsRepository;
import com.example.Credit_Card_Application.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
            throw new CardServiceException("Card not found with Id:- "+id);
        }
    }

    @Transactional
    public String addCard(CardHolderDetails cardHolderDetails, CardType cardType) {
        try {
            System.out.println("Starting card creation");

            // Save CardHolderDetails first if not already saved
            if (cardHolderDetails.getCardHolderId() == null) {
//                System.out.println("Saving new CardHolderDetails: " + cardHolderDetails);
                cardHolderDetailsRepository.save(cardHolderDetails);
            }

            // Generate card details
            Card newCard = generateCardDetails();
            newCard.setCardType(cardType);
            newCard.setCardHolderDetails(cardHolderDetails);

            // Save the new card
//            System.out.println("Card details to save: " + newCard);
            cardRepository.save(newCard);
//            System.out.println("Card saved successfully: " + newCard);

            return "Card saved successfully";
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to create card: " + e.getMessage());
            throw new CardServiceException("Failed to create card");
        }
    }




    @Transactional
    public String updateCard(Long id, CardHolderDetails cardHolderDetails) {
        try {
            Optional<Card> existingCard = cardRepository.findById(id);

            if (existingCard.isPresent()) {
                Card cardToUpdate = existingCard.get();

                // Check if the CardHolderDetails is existing
                if (cardHolderDetails.getCardHolderId() != null) {
                    Optional<CardHolderDetails> existingCardHolderDetails = cardHolderDetailsRepository.findById(cardHolderDetails.getCardHolderId());
                    if (existingCardHolderDetails.isPresent()) {
                        CardHolderDetails detailsToUpdate = existingCardHolderDetails.get();
                        detailsToUpdate.setCardHolderName(cardHolderDetails.getCardHolderName());
                        detailsToUpdate.setEmail(cardHolderDetails.getEmail());
                        detailsToUpdate.setContactNo(cardHolderDetails.getContactNo());
                        cardHolderDetails = cardHolderDetailsRepository.save(detailsToUpdate);
                    } else {
                        cardHolderDetails = cardHolderDetailsRepository.save(cardHolderDetails);
                    }
                } else {
                    cardHolderDetails = cardHolderDetailsRepository.save(cardHolderDetails);
                }

                // Update the card with the persisted cardHolderDetails
                cardToUpdate.setCardHolderDetails(cardHolderDetails);

                // Save the updated card
                cardRepository.save(cardToUpdate);

                return "Card updated successfully";
            } else {
                throw new CardServiceException("Card not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException("Failed to update card: " + e.getMessage());
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
        long cardNumber = Math.abs(rand.nextLong() % 900000000L) + 100000000L; // Generate a 9-digit card number
        String expiryDate = String.format("%02d/%04d", rand.nextInt(12) + 1, rand.nextInt(10) + 2023); // Generate MM/YYYY format for expiry date
        int cvv = rand.nextInt(900) + 100; // Generate a 3-digit CVV
        Card cardDetails = new Card(cardNumber, "Credit Card", expiryDate, cvv);
        return cardDetails;
    }

    public Optional<Card> getCard(Long id) {
        return cardRepository.findById(id);
    }

}
