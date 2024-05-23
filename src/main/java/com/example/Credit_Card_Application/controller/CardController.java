package com.example.Credit_Card_Application.controller;

import com.example.Credit_Card_Application.model.Card;
import com.example.Credit_Card_Application.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/card/edit")
    public Map<String, String> addNewCard(@RequestBody Card card) {
        String newCardResult = cardService.updateCard(card);
        Map<String, String> map = new HashMap<>();
        map.put("Result", newCardResult);
        return map;
    }

    @DeleteMapping("/card/{id}")
    public Map<String, String> deleteCard(@PathVariable Long id) {
        String deleteCardResult = cardService.deleteCard(id);
        Map<String, String> map = new HashMap<>();
        map.put("Result", deleteCardResult);
        return map;
    }
}
