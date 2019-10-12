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
     * @param monsters num of normal monsters
     * @param epics num of epic monsters
     * @param magics num of magic
     * @param buffs num of buffs
     * @param debuffs num of debuffs
     * @return
     */
    public Deck buildDeck(int monsters, int epics, int magics, int buffs, int debuffs) {
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
