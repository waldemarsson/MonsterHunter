package com.company.tests.factories;

import com.company.game.factories.DeckFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

    }

    @Test
    void deckIsRightSize() {

    }

    @Test
    void deckIsRightAmountOfMonsters() {

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