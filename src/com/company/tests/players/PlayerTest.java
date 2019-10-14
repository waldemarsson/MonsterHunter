package com.company.tests.players;

import com.company.game.collections.Deck;
import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Deck deck = new DeckFactory().buildDeck(10, 10, 10, 10, 10);

    @Test
    void constructorTest(){
        try{
            new Player("Player_1", deck);
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void playerHasValidNameUpperCase(){
        assertEquals("PLAYER_1",
                new Player("Player1", deck));
    }

    @Test
    void playerHasValidHp(){
        Player player = new Player("Player_1", deck);
        assertEquals(20, player.getHp());
    }

    @Test
    void playerHasValidDamage(){
        Player player = new Player("Player_1", deck);
        assertEquals(0, player.getDamage());
    }

    @Test
    void playerHasCorrectDeck(){
        try {
            Field field = Player.class.getDeclaredField("deck");
            field.setAccessible(true);
            Deck compareDeck = (Deck) field.get(new Player("Player_1", deck));
            assertNotNull(compareDeck);
            assertEquals(deck, compareDeck);
            assertEquals(50, compareDeck.getCards().size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void playerHasCorrectHand(){
        
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

    @Test
    void healPlayer(){

    }
}