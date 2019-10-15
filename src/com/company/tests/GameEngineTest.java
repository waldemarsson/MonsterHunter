package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.factories.DeckFactory;
import com.company.game.factories.MagicCardFactory;
import com.company.game.factories.MonsterCardFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    DeckFactory deckFactory = new DeckFactory();
    RoundCounter roundCounter;
    GameEngine gameEngine;
    Player[] players;

    @BeforeEach
    void setup() {
        roundCounter = new RoundCounter();
        Player player1 = new Player("Player1", deckFactory.buildDeck(20, 5, 15, 5, 5));
        Player player2 = new Player("Player2", deckFactory.buildDeck(20, 5, 15, 5, 5));
        players = new Player[]{player1, player2};
        gameEngine = new GameEngine(players, roundCounter);
    }

    @Test
    void engageMonsterVsMonster() {
    }


    @Test
    void engageMonsterVsMonsterAttackerWin() {

    }

    @Test
    void engageMonsterVsMonsterDefenderWin() {

    }

    @Test
    void engageMonsterVsMonsterNullIn() {

    }

    @Test
    void testEngage() {
    }

    @Test
    void testEngage1() {
    }
}