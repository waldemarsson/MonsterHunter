package com.company.tests.collections;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.cards.EffectCard;
import com.company.game.cards.MonsterCard;
import com.company.game.collections.Deck;
import com.company.game.factories.EffectCardFactory;
import com.company.game.factories.MagicCardFactory;
import com.company.game.factories.MonsterCardFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck;
    EffectCardFactory effectCardFactory = new EffectCardFactory();
    MagicCardFactory magicCardFactory = new MagicCardFactory();
    MonsterCardFactory monsterCardFactory = new MonsterCardFactory();
    List<Card> list;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < 15; i++) {
            list.add(monsterCardFactory.buildCard(id));
            id++;
        }
        for (int i = 0; i < 5; i++) {
            list.add(monsterCardFactory.buildCard(id, (BuffCard) effectCardFactory.buildEffectCard(0, true)));
            id++;
        }
        for (int i = 0; i < 10; i++) {
            list.add(magicCardFactory.buildCard(id));
            id++;
        }
        for (int i = 0; i < 5; i++) {
            list.add(effectCardFactory.buildEffectCard(id, true));
            id++;
        }
        for (int i = 0; i < 5; i++) {
            list.add(effectCardFactory.buildEffectCard(id, false));
            id++;
        }
        deck = new Deck(list);

    }

    @Nested
    @DisplayName("TESTING CONSTRUCTOR")
    class constructorTest {

        @Test
        void create() {
            try {
                deck = new Deck(list);
                deck = new Deck(new ArrayList<>());
            } catch (Exception e) {
                fail();
            }
        }
    }

    @Test
    void hasCardsNoCards() {
        Deck deckNoCards = new Deck(new ArrayList<>());
        assertNotNull(deckNoCards.getCards());
        assertFalse(deckNoCards.hasCards());
    }

    @Test
    void hasCards() {
        assertNotNull(deck.getCards());
        assertTrue(deck.hasCards());
        
        assertFalse(new Deck(new ArrayList<>()).hasCards());
    }

    @Test
    void drawCard() {
        int sizeOfDeck = deck.getCards().size();
        for (int i = 0; i <= sizeOfDeck; i++) {
            if (deck.hasCards()) {
                Card card = deck.drawCard();
                assertNotNull(card);
                assertTrue(card.getId() >= 0);
                assertTrue(card.toString().length() > 0);
            }
        }
        assertNull(deck.drawCard());
    }

    @Test
    void cardsLeft() {
        int sizeOfDeck = deck.getCards().size();
        for (int i = 0; i <= sizeOfDeck; i++) {
            if (deck.hasCards()) {
                assertEquals(sizeOfDeck - i, deck.cardsLeft());
                deck.drawCard();
            }

        }
        assertEquals(0, deck.cardsLeft());
    }
}