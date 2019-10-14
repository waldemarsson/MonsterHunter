package com.company.tests.collections;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.collections.Hand;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    }

    @Test
    void playCardOneCardOnHand(){

    }

    @Test
    void doesNotHaveCard() {

    }

    @Test
    void hasCard(){

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
}