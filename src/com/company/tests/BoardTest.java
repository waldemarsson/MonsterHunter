package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.cards.DebuffCard;
import com.company.game.cards.MonsterCard;
import com.company.game.collections.Hand;
import com.company.game.enums.EffectType;
import com.company.game.factories.DeckFactory;
import com.company.game.factories.EffectCardFactory;
import com.company.game.factories.MonsterCardFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    DeckFactory deckFactory = new DeckFactory();
    RoundCounter roundCounter;
    Board board;
    Player[] players;
    MonsterCardFactory monsterCardFactory = new MonsterCardFactory();
    List[] monsterPiles;


    @BeforeEach
    void setUp() {
        roundCounter = new RoundCounter();
        Player player1 = new Player("Player1", deckFactory.buildDeck(20, 5, 15, 5, 5));
        Player player2 = new Player("Player2", deckFactory.buildDeck(20, 5, 15, 5, 5));
        players = new Player[]{player1, player2};
        board = new Board(roundCounter, players);

        try {
            Field field = board.getClass().getDeclaredField("monsterPiles");
            field.setAccessible(true);
            monsterPiles = (List[]) field.get(board);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getMonsterPilePlayer1() {
        assertNotNull(board.getMonsterPile(0));
    }

    @Test
    void getMonsterPilePlayer2() {
        assertNotNull(board.getMonsterPile(1));
    }

    @Test
    void getMonsterPilePlayer3() {
        assertNull(board.getMonsterPile(3));
    }

    @Test
    void getMonsterPileWithMonsters() {
        assertNotNull(board.getMonsterPile(0));
    }


    @Test
    void placeCardOnBoard() {
        assertEquals(0, monsterPiles[roundCounter.getTurn()].size());
        assertTrue(board.placeMonsterOnBoard(monsterCardFactory.buildCard(1)));
        assertEquals(1, monsterPiles[roundCounter.getTurn()].size());
    }

    @Test
    void placeBuffOwnCard() {
        board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
        BuffCard buff = (BuffCard) new EffectCardFactory().buildEffectCard(2, true);
        MonsterCard target = (MonsterCard) monsterPiles[roundCounter.getTurn()].get(0);

        assertEquals(0, target.getBuffCard().getValue());
        assertTrue(board.placeEffectOnMonsterWithId(buff, 1));
        assertTrue(target.getBuffCard().getValue() > 0);
    }

    @Test
    void placeDebuffOwnCard() {
        board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
        DebuffCard debuff = (DebuffCard) new EffectCardFactory().buildEffectCard(2, false);
        MonsterCard target = (MonsterCard) monsterPiles[roundCounter.getTurn()].get(0);

        assertEquals(0, target.getDebuffCard().getValue());
        assertFalse(board.placeEffectOnMonsterWithId(debuff, 1));
        assertTrue(target.getDebuffCard().getValue() == 0);
    }

    @Test
    void placeDebuffOpponentCard() {
        board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
        MonsterCard target = (MonsterCard) monsterPiles[roundCounter.getTurn()].get(0);
        roundCounter.nextTurn();

        DebuffCard debuff = (DebuffCard) new EffectCardFactory().buildEffectCard(2, false);

        assertEquals(0, target.getDebuffCard().getValue());
        assertTrue(board.placeEffectOnMonsterWithId(debuff, 1));
        assertTrue(target.getDebuffCard().getValue() < 0);
    }

    @Test
    void placeBuffOpponentCard() {
        board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
        MonsterCard target = (MonsterCard) monsterPiles[roundCounter.getTurn()].get(0);
        roundCounter.nextTurn();

        BuffCard buff = (BuffCard) new EffectCardFactory().buildEffectCard(2, true);
        assertEquals(0, target.getBuffCard().getValue());
        assertFalse(board.placeEffectOnMonsterWithId(buff, 1));
        assertTrue(target.getBuffCard().getValue() == 0);
    }


    @Test
    void attackMonsterWithMonster() {
    }

    @Test
    void useMagicOnMonster() {
    }

    @Test
    void useMagic() {
    }
}