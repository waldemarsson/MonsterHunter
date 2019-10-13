package com.company.game.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.collections.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
     * @param epics    num of epic monsters
     * @param magics   num of magic
     * @param buffs    num of buffs
     * @param debuffs  num of debuffs
     * @return
     */
    public Deck buildDeck(int monsters, int epics, int magics, int buffs, int debuffs) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(buildMonsterCards(monsters));
        cards.addAll(buildEpicCards(epics));
        cards.addAll(buildMagicCards(magics));
        cards.addAll(buildBuffCards(buffs));
        cards.addAll(buildDebuffCards(debuffs));
//        return new Deck(cards);
        return shuffle(new Deck(cards));
    }

    private void increaseCreatedCards() {
        createdCards++;
    }


    private List<Card> buildMonsterCards(int numOfCards) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            cards.add(monsterCardFactory.buildCard(createdCards));
            increaseCreatedCards();
        }
        return cards;
    }

    private List<Card> buildEpicCards(int numOfCards) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            cards.add(monsterCardFactory.buildCard(createdCards, (BuffCard) effectCardFactory.buildEffectCard(0, true)));
            increaseCreatedCards();
        }
        return cards;
    }

    private List<Card> buildMagicCards(int numOfCards) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            cards.add(magicCardFactory.buildCard(createdCards));
            increaseCreatedCards();
        }
        return cards;
    }

    private List<Card> buildBuffCards(int numOfCards) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            cards.add(effectCardFactory.buildEffectCard(createdCards, true));
            increaseCreatedCards();
        }
        return cards;
    }

    private List<Card> buildDebuffCards(int numOfCards) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numOfCards; i++) {
            cards.add(effectCardFactory.buildEffectCard(createdCards, false));
            increaseCreatedCards();
        }
        return cards;
    }

    /**
     * @param deck
     * @return new shuffled Deck
     */
    private Deck shuffle(Deck deck) {
        Collections.shuffle(deck.getCards());
        return new Deck(deck.getCards());
    }
}
