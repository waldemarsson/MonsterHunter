package com.company.tests.factories;

import com.company.game.cards.MonsterCard;
import com.company.game.collections.Deck;
import com.company.game.factories.DeckFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class DeckFactoryTest {

    DeckFactory deckFactory;

    @Nested
    class testConstructor {

        @Test
        void createFactory() {
            try {
                new DeckFactory();
            } catch (Exception e) {
                fail();
            }
        }
    }

    @BeforeEach
    void setUp() {
        deckFactory = new DeckFactory();
    }

    @Test
    void buildDeck() {
        Deck deck = deckFactory.buildDeck(10, 10, 10, 10, 10);
        assertNotNull(deck);
    }

    @Test
    void buildDeckNegValue() {
        Deck deck = deckFactory.buildDeck(-10, 10, 10, 10, 10);
        assertNotNull(deck);
    }

    @Test
    void deckIsRightSize() {
        for (int i = 0; i < 50; i++) {
            int numMonster = ThreadLocalRandom.current().nextInt(5, 15);
            int numEpics = ThreadLocalRandom.current().nextInt(5, 15);
            int numMagics = ThreadLocalRandom.current().nextInt(5, 15);
            int numBuffs = ThreadLocalRandom.current().nextInt(5, 15);
            int numDebuffs = ThreadLocalRandom.current().nextInt(5, 15);
            int total = numMonster + numEpics + numMagics + numBuffs + numDebuffs;

            Deck deck = deckFactory.buildDeck(numMonster, numEpics, numMagics, numBuffs, numDebuffs);
            assertEquals(total, deck.cardsLeft());
        }
    }

    @Test
    void deckIsRightAmountOfMonsters() {
        for (int i = 0; i < 100; i++) {
            int numMonster = ThreadLocalRandom.current().nextInt(5, 50);
            Deck deck = deckFactory.buildDeck(numMonster, 10, 10, 10, 10);
            assertEquals(numMonster, deck.getCards().stream().filter(card -> card.getClass() == MonsterCard.class).filter(card -> ((MonsterCard) card).getBonus().getValue() == 0).count());
        }
    }

    @Test
    void deckIsRightAmountOfEpics() {

    }

    @Test
    void deckIsRightAmountOfMagics() {

    }

    @Test
    void deckIsRightAmountOfBuffs() {

    }

    @Test
    void deckIsRightAmountOfDeuffs() {

    }

    @Test
    void shuffle() {
    }
}