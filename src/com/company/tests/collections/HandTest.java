package com.company.tests.collections;

import com.company.game.cards.Card;
import com.company.game.collections.Hand;
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
    void playCard() {

    }

    @Test
    void putCard() {
    }

    @Test
    void hasCard() {
    }

    @Test
    void hasCards() {
    }
}