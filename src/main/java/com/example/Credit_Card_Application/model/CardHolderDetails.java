package com.example.Credit_Card_Application.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "card_holer_details")
public class CardHolderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_holder_id")
    private int cardHolderId;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    private String email;

    @Column(name = "contact_no")
    private long contactNo;

    @JsonIgnore
    @OneToMany(mappedBy = "cardHolderDetails", cascade = CascadeType.ALL)
    private List<Card> cards;

    public CardHolderDetails() {
    }

    public CardHolderDetails(String cardHolderName, String email, long contactNo, List<Card> cards) {
        this.cardHolderName = cardHolderName;
        this.email = email;
        this.contactNo = contactNo;
        this.cards = cards;
    }

    public int getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(int cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
