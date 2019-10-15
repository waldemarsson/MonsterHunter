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

    Deck monsterDeck = new DeckFactory().buildDeck(50, 0,0,0,0);
    private static Deck getFreshDeck() { return new DeckFactory().buildDeck(10, 10, 10, 10, 10); }

    @Test
    void constructorTest(){
        try{
            new Player("Player_1", getFreshDeck());
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void playerHasValidNameUpperCase(){
        assertEquals("PLAYER_1",
                new Player("Player1", getFreshDeck()));
    }

    @Test
    void playerNullName(){
        Player player = new Player(null, getFreshDeck());
        assertNotNull(player.getName());
        assertEquals("UNKNOWN_PLAYER", player.getName());
    }

    @Test
    void playerNoName(){
        Player player = new Player("", getFreshDeck());
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
        Player player = new Player("Player_1", getFreshDeck());
        assertEquals(20, player.getHp());
    }

    @Test
    void playerHasValidDamage(){
        Player player = new Player("Player_1", getFreshDeck());
        assertEquals(0, player.getDamage());
    }

    @Test
    void playerHasCorrectDeck(){
        Deck localDeck = getFreshDeck();
        try {
            Field field = Player.class.getDeclaredField("deck");
            field.setAccessible(true);
            Deck compareDeck = (Deck) field.get(new Player("Player_1", localDeck));
            assertNotNull(compareDeck);
            assertEquals(localDeck, compareDeck);
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
            Hand hand = (Hand) handField.get(new Player("Player_1", getFreshDeck()));
            assertNotNull(hand);
            assertTrue(hand.hasCards());
            assertEquals(5, hand.getCardsOnHandAsString().size());
        } catch (Exception e) {
            fail();
        }
    }



    @Test
    void addDamage() {
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(10);
        assertEquals(10, player.getDamage());
    }

    @Test
    void addNegativeDamage(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(-1);
        assertEquals(20, player.getDamage());
    }

    @Test
    void addZeroDamage(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(0);
        assertEquals(20, player.getDamage());
    }

    @Test
    void addDamageBeyondMaxHp(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(1000);
        assertEquals(1000, player.getDamage());
    }


    @Test
    void drawCardFromDeckToHand(){
        Player player = new Player("Player_1", getFreshDeck());
        player.drawFromDeckToHand();
        assertEquals(6, player.getHand().getCardsOnHandAsString().size());
    }

    @Test
    void putCardOnBoardFromHand(){
        try {
            RoundCounter roundCounter = new RoundCounter();
            Player p1 = new Player("Player_1", monsterDeck);
            Player p2 = new Player("Player_2", getFreshDeck());
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
        Player player = new Player("Player_1", getFreshDeck());
        assertIterableEquals(player.getHandAsString(), player.getHand().getCardsOnHandAsString());
    }

    @Test
    void isAliveSmallDamage(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(1);
        assertFalse(player.isAlive());
    }

    @Test
    void isAliveFullDamage() {
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(player.getHp());
        assertFalse(player.isAlive());
    }

    @Test
    void isDead(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(player.getHp() + 1);
        assertFalse(player.isAlive());
    }

    @Test
    void healPlayer1HP(){
        Player player = new Player("Player_1", getFreshDeck());
        player.addDamage(10);
        player.heal(1);
        assertEquals(9, player.getDamage());
    }

    @Test
    void healPlayer0Hp(){
        Player player = new Player("", getFreshDeck());
        player.addDamage(1);
        player.heal(0);
        assertEquals(1, player.getDamage());
    }

    @Test
    void healPlayerNegativeHp(){
        Player player = new Player("", getFreshDeck());
        player.addDamage(10);
        player.heal(-10);
        assertEquals(10, player.getDamage());
    }

    @Test
    void healPlayerBeyondZero(){
        Player player = new Player("", getFreshDeck());
        player.addDamage(10);
        player.heal(1000);
        assertEquals(0, player.getDamage());
    }

    @Test
    void healPlayerWithNoDamage(){
        Player player = new Player("Player_1", getFreshDeck());
        player.heal(10);
        assertEquals(0, player.getDamage());
    }

}