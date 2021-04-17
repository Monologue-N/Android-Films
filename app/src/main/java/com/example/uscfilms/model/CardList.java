package com.example.uscfilms.model;

import java.util.ArrayList;

public class CardList {
    private String subtitle;
    private ArrayList<SingleCard> cardList;

    public CardList() {
    }

    public CardList(String subtitle, ArrayList<SingleCard> cardList) {
        this.subtitle = subtitle;
        this.cardList = cardList;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ArrayList<SingleCard> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<SingleCard> cardList) {
        this.cardList = cardList;
    }
}
