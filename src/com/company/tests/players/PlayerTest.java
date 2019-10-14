package com.company.tests.players;

import com.company.game.collections.Deck;
import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void constructorTest(){
        try{
            new Player("Player_1", new DeckFactory().buildDeck(10, 10, 10, 10, 10));
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void playerHasValidNameUpperCase(){
        assertEquals("PLAYER_1",
                new Player("Player1", new DeckFactory()
                        .buildDeck(10, 10, 10, 10, 10)).getName());
    }

    @Test
    void playerHasValidHpAndDamage(){

    }

    @Test
    void playerHasDeckAndHand(){

    }

    @Test
    void constructorDrawsFiveCardsToHand(){

    }

    @Test
    void addDamage() {

    }


    @Test
    void drawCardFromDeckToHand(){

    }

    @Test
    void putCardOnBoardFromHand(){

    }

    @Test
    void getHandAsString() {

    }

    @Test
    void isAlive() {

    }
}