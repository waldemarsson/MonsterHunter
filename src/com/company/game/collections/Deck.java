package com.company.game.collections;

import com.company.game.cards.Card;

import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }


    /**
     * @return true if cards != empty
     */
    public boolean hasCards() {
        return false;
    }


    /**
     * @return a Card at index 0 from cards, removes Card at the same time
     */
    public Card drawCard(){
        return null;
    }

    /**
     * @return size of cards
     */
    public int cardsLeft() {
        return 0;
    }


}
