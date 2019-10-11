package com.company.tests.cards;

import com.company.game.cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {


    /**
     * Implementation of abstract class Card needed for test
     */
    class CardSuperTest extends Card {
        public CardSuperTest(int id, String name) {
            super(id, name);
        }
    }

    @Test
    void constructorFailureTest(){
        try{
            new CardSuperTest(0 , "name");
        } catch(Exception e){
            fail("Constructor threw unhandled Exception");
        }
    }

    @Test
    void belowZeroIdTest(){
        assertEquals(0, new CardSuperTest(-1, "name").getId(), "Failed to set id to zero if negative param");
    }


    @Test
    void nullNameTest(){
        assertNotNull(new CardSuperTest(1, null).getName(), "Failed to set name to valid string if param was null");
    }

    @Test
    void emptyNameTest(){
        assertEquals("", new CardSuperTest(1, "").getName(), "Failed to set empty name");
    }

    @Test
    void onlyEnglishAlphabetNameTest(){
        assertTrue(new CardSuperTest(1, "h14ell4i0i!)'\"åäöÅÄÖ").getName().matches("(?i)^[a-z]*$"),
                "Failed to convert all name chars to a-z");
    }

    @Test
    void alwaysUpperCaseNameTest(){
        assertTrue(new CardSuperTest(1, "hello").getName().matches("^[A-Z]*$"), "Failed to force name to upper case");
    }


    @Test
    void testToString() {
        assertEquals("HELLO_1: ", new CardSuperTest(1, "hello").toString(),
                "Failed to get proper toString for card");
    }
}