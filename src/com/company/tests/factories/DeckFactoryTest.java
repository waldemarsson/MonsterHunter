package com.company.tests.factories;

import com.company.game.cards.*;
import com.company.game.collections.Deck;
import com.company.game.factories.DeckFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        assertEquals(50, deck.getCards().size());
        assertNotNull(deck);
    }

    @Test
    void buildDeckNegValue() {
        Deck deck = deckFactory.buildDeck(-10, 10, 10, 10, 10);
        assertEquals(40, deck.getCards().size());
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
            assertEquals(numMonster + 40, deck.cardsLeft());
            assertEquals(numMonster, deck.getCards().stream().filter(card -> card.getClass() == MonsterCard.class).filter(card -> ((MonsterCard) card).getBonus().getValue() == 0).count());
        }
    }

    @Test
    void deckIsRightAmountOfEpics() {
        for (int i = 0; i < 100; i++) {
            int numEpics = ThreadLocalRandom.current().nextInt(5, 50);
            Deck deck = deckFactory.buildDeck(10, numEpics, 10, 10, 10);
            assertEquals(numEpics + 40, deck.cardsLeft());
            assertEquals(numEpics, deck.getCards().stream().filter(card -> card.getClass() == MonsterCard.class).filter(card -> ((MonsterCard) card).getBonus().getValue() > 0).count());
        }

    }

    @Test
    void deckIsRightAmountOfMagics() {
        for (int i = 0; i < 100; i++) {
            int numMagics = ThreadLocalRandom.current().nextInt(5, 50);
            Deck deck = deckFactory.buildDeck(10, 10, numMagics, 10, 10);
            assertEquals(numMagics + 40, deck.cardsLeft());
            assertEquals(numMagics, deck.getCards().stream().filter(card -> card.getClass() == MagicCard.class).count());
        }

    }

    @Test
    void deckIsRightAmountOfBuffs() {
        for (int i = 0; i < 100; i++) {
            int numBuffs = ThreadLocalRandom.current().nextInt(5, 50);
            Deck deck = deckFactory.buildDeck(10, 10, 10, numBuffs, 10);
            assertEquals(numBuffs + 40, deck.cardsLeft());
            assertEquals(numBuffs, deck.getCards().stream().filter(card -> card.getClass() == BuffCard.class).count());
        }
    }

    @Test
    void deckIsRightAmountOfDebuffs() {
        for (int i = 0; i < 100; i++) {
            int numDebuffs = ThreadLocalRandom.current().nextInt(5, 50);
            Deck deck = deckFactory.buildDeck(10, 10, 10, 10, numDebuffs);
            assertEquals(numDebuffs + 40, deck.cardsLeft());
            assertEquals(numDebuffs, deck.getCards().stream().filter(card -> card.getClass() == DebuffCard.class).count());
        }
    }

    @Test
    void shuffle() {
        for (int i = 0; i < 100; i++) {

            Deck deck = deckFactory.buildDeck(10, 10, 10, 10, 10);
            assertEquals(50, deck.cardsLeft());
            List<Card> monsters = deck.getCards().subList(0, 10);
            List<Card> epics = deck.getCards().subList(10, 20);
            List<Card> magics = deck.getCards().subList(20, 30);
            List<Card> buffs = deck.getCards().subList(30, 40);
            List<Card> debuffs = deck.getCards().subList(40, 50);

            List<Card> allCards = Stream.of(monsters, epics, magics, buffs, debuffs)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            assertEquals(50, allCards.size());
            assertTrue(deck.getCards().containsAll(allCards));

            assertTrue(monsters.stream().filter(card -> card.getClass() == MonsterCard.class).filter(card -> ((MonsterCard) card).getBonus().getValue() == 0).count() < 10);
            assertTrue(epics.stream().filter(card -> card.getClass() == MonsterCard.class).filter(card -> ((MonsterCard) card).getBonus().getValue() > 0).count() < 10);
            assertTrue(magics.stream().filter(card -> card.getClass() == MagicCard.class).count() < 10);
            assertTrue(buffs.stream().filter(card -> card.getClass() == BuffCard.class).count() < 10);
            assertTrue(debuffs.stream().filter(card -> card.getClass() == DebuffCard.class).count() < 10);
        }
    }
}