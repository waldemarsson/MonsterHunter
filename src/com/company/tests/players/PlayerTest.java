package com.company.tests.players;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.cards.Card;
import com.company.game.collections.Deck;
import com.company.game.collections.Hand;
import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Deck deck = new DeckFactory().buildDeck(10, 10, 10, 10, 10);
    Deck monsterDeck = new DeckFactory().buildDeck(50, 0,0,0,0);

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
    void playerNullName(){
        Player player = new Player(null, deck);
        assertNotNull(player.getName());
        assertEquals("UNKNOWN_PLAYER", player.getName());
    }

    @Test
    void playerNoName(){
        Player player = new Player("", deck);
        assertEquals("UNKNOWN_PLAYER", player.getName());
    }

    @Test
    void nullDeck(){
        try{
            Field field = Player.class.getDeclaredField("deck");
            field.setAccessible(true);
            assertNotNull(field.get(new Player("Player_1", null)));
        } catch (Exception e){
            fail();
        }
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
        Player player = new Player("Player_1", deck);
        player.drawFromDeckToHand();
        assertEquals(6, player.getHand().getCardsOnHandAsString().size());
    }

    @Test
    void putCardOnBoardFromHand(){
        try {
            RoundCounter roundCounter = new RoundCounter();
            Player p1 = new Player("Player_1", monsterDeck);
            Player p2 = new Player("Player_2", deck);
             Board board = new Board(roundCounter, new Player[]{p1, p2});
            p1.setBoard(board);
            p2.setBoard(board);
            Field field = Hand.class.getDeclaredField("cardsOnHand");
            field.setAccessible(true);
            List<Card> cards = (List<Card>) field.get(p1);
            int cardsOnHand = cards.size();
            assertEquals(0, board.getMonsterPile(roundCounter.getTurn()).size());
            assertTrue(p1.placeCardOnBoardFromHand(cards.get(0).getId()));
            assertNotEquals(cardsOnHand, cards.size());
            assertEquals(1, board.getMonsterPile(roundCounter.getTurn()).size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getHandAsString() {
        Player player = new Player("Player_1", deck);
        assertIterableEquals(player.getHandAsString(), player.getHand().getCardsOnHandAsString());
    }

    @Test
    void isAlive() {

    }

    @Test
    void healPlayer(){

    }
}