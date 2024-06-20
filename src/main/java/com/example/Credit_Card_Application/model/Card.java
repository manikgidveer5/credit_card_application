package com.example.Credit_Card_Application.model;


import javax.persistence.*;

@Entity
@Table(name = "card", schema = "credit_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "card_id")
    private Long cardID;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "card_name")
    private String cardName;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "expiry_date")
    private String expiryDate;

    private int cvv;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_holder_id", referencedColumnName = "card_holder_id")
    private CardHolderDetails cardHolderDetails;

    public Card() {
    }

    public Card(Long cardNumber, String cardName, String expiryDate, int cvv) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public Long getCardID() {
        return cardID;
    }

    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public CardHolderDetails getCardHolderDetails() {
        return cardHolderDetails;
    }

    public void setCardHolderDetails(CardHolderDetails cardHolderDetails) {
        this.cardHolderDetails = cardHolderDetails;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardID=" + cardID +
                ", cardNumber=" + cardNumber +
                ", cardName='" + cardName + '\'' +
                ", cardType=" + cardType +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv=" + cvv +
                ", cardHolderDetails=" + cardHolderDetails +
                '}';
    }
}
