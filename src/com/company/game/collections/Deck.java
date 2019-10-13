package com.company.game.collections;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.cards.EffectCard;
import com.company.game.enums.EffectType;

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
        return !cards.isEmpty();
    }


    /**
     * @return a Card at index 0 from cards, removes Card at the same time
     */
    public Card drawCard() {
        if (!hasCards()) return null;

        return cards.remove(0);
    }

    /**
     * @return size of cards
     */
    public int cardsLeft() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

}
