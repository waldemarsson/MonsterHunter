package com.company.game.collections;

import com.company.game.cards.Card;

import java.net.Inet4Address;
import java.util.*;
import java.util.stream.Collectors;

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
        Optional optional = cardsOnHand.stream().filter(c -> c.getId() == id).findFirst();
        Card card = null;
        if(optional.isPresent()){
            card = (Card) optional.get();
            cardsOnHand.remove(card);
        }
        return card;
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

    public List<String> getCardsOnHandAsString(){
        return cardsOnHand.stream().sorted(Comparator.comparingInt(Card::getId)).map(Card::toString).collect(Collectors.toList());
    }
}
