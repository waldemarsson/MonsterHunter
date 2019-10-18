package com.company.tests.collections;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.collections.Deck;
import com.company.game.collections.Hand;
import com.company.game.enums.EffectType;
import com.company.game.factories.DeckFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void constructorTest(){
        try{
            new Hand();
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void constructorCreatedList(){
        try{
            Field field = Hand.class.getDeclaredField("cardsOnHand");
            field.setAccessible(true);
            List<Card> cards = (List) field.get(new Hand());
            assertTrue(cards instanceof ArrayList);
        } catch (Exception e){
            fail();
        }
    }


    @Test
    void playCardEmpty(){
        assertNull(new Hand().playCard(1));
    }

    @Test
    void playWrongCard(){
        Hand hand = new Hand();
        hand.putCard(new BuffCard(1, 2, EffectType.DEFENSE));
        assertNull(hand.playCard(2));
    }

    @Test
    void playCardOneCardOnHand(){
        Hand hand = new Hand();
        hand.putCard(new BuffCard(1, 2, EffectType.DEFENSE));
        assertNotNull(hand.playCard(1));
    }

    @Test
    void doesNotHaveCard() {
        Hand hand = new Hand();
        hand.putCard(new BuffCard(1,2,EffectType.DEFENSE));
        assertFalse(hand.hasCard(2));
    }

    @Test
    void hasCard(){
        Hand hand = new Hand();
        hand.putCard(new BuffCard(1, 2, EffectType.DEFENSE));
        assertTrue(hand.hasCard(1));
    }

    @Test
    void hasNoCards(){
        try {
            Field field = Hand.class.getDeclaredField("cardsOnHand");
            field.setAccessible(true);
            List<Card> list = (List<Card>) field.get(new Hand());
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Testing both putCard and hasCards in same test
     */
    @Test
    void putCardAndHasCards() {
        try {
            Hand hand = new Hand();
            hand.putCard(new BuffCard(2 ,1, EffectType.STAMINA));
            Field field = Hand.class.getDeclaredField("cardsOnHand");
            field.setAccessible(true);
            List<Card> list = (List<Card>) field.get(hand);
            assertTrue(list.size() == 1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void putNullAndHasNoCards() {
        try {
            Hand hand = new Hand();
            hand.putCard(null);
            Field field = Hand.class.getDeclaredField("cardsOnHand");
            field.setAccessible(true);
            List<Card> list = (List<Card>) field.get(hand);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testHandAsString(){
        try{
            Deck deck = new DeckFactory().buildDeck(10, 10, 10, 10, 10);
            Hand hand = new Hand();
            Field field = Deck.class.getDeclaredField("cards");
            field.setAccessible(true);
            List<String> cards = deck.getCards().stream().sorted(Comparator.comparingInt(Card::getId)).map(Card::toString).collect(Collectors.toList());
            while (deck.hasCards()){
                hand.putCard(deck.drawCard());
            }
            assertIterableEquals(cards, hand.getCardsOnHandAsString());
        } catch (Exception e){
            fail();
        }

    }
}