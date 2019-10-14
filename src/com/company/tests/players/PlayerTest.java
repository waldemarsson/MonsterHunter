package com.company.tests.players;

import com.company.game.collections.Deck;
import com.company.game.collections.Hand;
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
        try {
            Field handField = Player.class.getDeclaredField("hand");
            handField.setAccessible(true);
            Hand hand = (Hand) handField.get(new Player("Player_1", deck));
            assertNotNull(hand);
            assertTrue(hand.hasCards());
            assertEquals(5, hand.getCardsOnHandAsString().size());
        } catch (Exception e) {
            fail();
        }
    }



    @Test
    void addDamage() {
        Player player = new Player("Player_1", deck);
        player.addDamage(10);
        assertEquals(10, player.getDamage());
    }

    @Test
    void addNegativeDamage(){
        Player player = new Player("Player_1", deck);
        player.addDamage(-1);
        assertEquals(20, player.getDamage());
    }

    @Test
    void addZeroDamage(){
        Player player = new Player("Player_1", deck);
        player.addDamage(0);
        assertEquals(20, player.getDamage());
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