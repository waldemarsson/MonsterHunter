package com.company.game.factories;

import com.company.game.collections.Deck;

public class DeckFactory {

    private int createdCards;
    private final EffectCardFactory effectCardFactory;
    private final MagicCardFactory magicCardFactory;
    private final MonsterCardFactory monsterCardFactory;

    public DeckFactory() {
        createdCards = 0;
        effectCardFactory = new EffectCardFactory();
        magicCardFactory = new MagicCardFactory();
        monsterCardFactory = new MonsterCardFactory();
    }


    /**
     * @implNote 20 monster, 10 magic, 5 buff, 5 debuff
     * @return Deck with 40 Cards
     */
    public Deck buildDeck() {
        return null;
    }

    /**
     * @param deck
     * @return new shuffled Deck
     */
    public Deck shuffle(Deck deck) {
        return null;
    }
}
