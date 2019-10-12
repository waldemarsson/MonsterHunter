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
        return cards.size() > 0;
    }


    /**
     * @return a Card at index 0 from cards, removes Card at the same time
     */
    public Card drawCard() {
        // TODO: Visste inte riktigt hur jag skulle göra här, skapa my klass och returnera den eller returnera null?
        return hasCards() ? cards.remove(0) : new BuffCard(0, 0, EffectType.NONE);
//        return cards.remove(0);
    }

    /**
     * @return size of cards
     */
    public int cardsLeft() {
        return 0;
    }

    public List<Card> getCards() {
        return cards;
    }

}
