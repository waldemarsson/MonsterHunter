package com.company.game.collections;

import com.company.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cardsOnHand;

    public Hand() {
        this.cardsOnHand = new ArrayList<>();
    }


    /**
     * @param id of card
     * @return Card with that id, remove from cardsOnHand
     */
    public Card playCard(int id) {
        return null;
    }


    /**
     * @param card
     * @implNote Add Card to cardsOnHand
     */
    public void putCard(Card card) {
        if(card != null){
            cardsOnHand.add(card);
        }
    }


    /**
     * @param id
     * @return boolean if cardsOnHand contains Card with id
     */
    public boolean hasCard(int id) {
        return cardsOnHand.stream().filter(card -> card.getId() == id).count() == 1;
    }


    /**
     * @return boolean if cardsOnHand != empty
     */
    public boolean hasCards() {
        return !cardsOnHand.isEmpty();
    }
}
