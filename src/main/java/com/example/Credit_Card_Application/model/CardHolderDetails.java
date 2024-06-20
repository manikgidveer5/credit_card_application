package com.example.Credit_Card_Application.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "card_holer_details")
public class CardHolderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_holder_id")
    private Long cardHolderId;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    private String email;

    @Column(name = "contact_no")
    private long contactNo;

    @JsonIgnore
    @OneToMany(mappedBy = "cardHolderDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards;

    public CardHolderDetails() {
    }

    public CardHolderDetails(String cardHolderName, String email, long contactNo, Set<Card> cards) {
        this.cardHolderName = cardHolderName;
        this.email = email;
        this.contactNo = contactNo;
        this.cards = cards;
    }

    public Long getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(Long cardHolderId) {
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

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "CardHolderDetails{" +
                "cardHolderId=" + cardHolderId +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", email='" + email + '\'' +
                ", contactNo=" + contactNo +
                ", cards=" + cards +
                '}';
    }
}
