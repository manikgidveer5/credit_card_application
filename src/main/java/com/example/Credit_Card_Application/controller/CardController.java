package com.example.Credit_Card_Application.controller;

import com.example.Credit_Card_Application.exception.CardServiceException;
import com.example.Credit_Card_Application.model.Card;
import com.example.Credit_Card_Application.model.CardHolderDetails;
import com.example.Credit_Card_Application.model.CardType;
import com.example.Credit_Card_Application.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        List<Card> card = cardService.getAllCards();
//        for (int i = 0; i < card.size(); i++) {
//            System.out.println(card.get(i).getCardName());
//        }
        return card;
    }

    @GetMapping("/card/search")
    public List<Card> searchCards(@RequestParam("term") String searchTerm) {
        System.out.println("searchTerm: "+ searchTerm);
        return cardService.searchCards(searchTerm);
    }

    @PostMapping("/card/add")
    public ResponseEntity<Map<String, String>> createCard(@RequestBody CardHolderDetails cardHolderDetails,@RequestParam("cardType") CardType cardType) {
        String result = cardService.addCard(cardHolderDetails,cardType);
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("card/edit/{id}")
    public ResponseEntity<Map<String, String>> updateCard(@PathVariable Long id, @RequestBody CardHolderDetails cardHolderDetails) {
        try {
            String message = cardService.updateCard(id, cardHolderDetails);
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (CardServiceException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/card/{id}")
    public Map<String, String> deleteCard(@PathVariable Long id) {
        String deleteCardResult = cardService.deleteCard(id);
        Map<String, String> map = new HashMap<>();
        map.put("Result", deleteCardResult);
        return map;
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<Optional<Card>> getCardById(@PathVariable Long id) {
        Optional<Card> card = cardService.getCardById(id);
        return ResponseEntity.ok(card);
    }
}
