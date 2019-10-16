package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.cards.BuffCard;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import com.company.game.factories.DeckFactory;
import com.company.game.factories.MagicCardFactory;
import com.company.game.factories.MonsterCardFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {
    DeckFactory deckFactory;
    MonsterCardFactory monsterCardFactory;
    RoundCounter roundCounter;
    GameEngine gameEngine;
    Player[] players;

    @BeforeEach
    void setup() {
        deckFactory = new DeckFactory();
        monsterCardFactory = new MonsterCardFactory();
        roundCounter = new RoundCounter();
        Player player1 = new Player("Player1", deckFactory.buildDeck(20, 5, 15, 5, 5));
        Player player2 = new Player("Player2", deckFactory.buildDeck(20, 5, 15, 5, 5));
        players = new Player[]{player1, player2};
        gameEngine = new GameEngine(players, roundCounter);
    }

    @Test
    void engageNull() {
        assertFalse(gameEngine.engage((MagicCard) null));
        assertFalse(gameEngine.engage((MonsterCard) null));
    }

    @Test
    void engageMonsterCardNullAndNull() {
        MonsterCard[] cards = gameEngine.engage((MonsterCard) null, null);
        assertNull(cards[0]);
        assertNull(cards[1]);
    }

    @Test
    void engageMagicCardNullAndNull () {
        List<MonsterCard> cards = gameEngine.engage((MagicCard) null, null);
        assertTrue(cards.isEmpty());
    }


    @Nested
    @DisplayName("TESTS engage Magic")
    class Magic {

        @Test
        void engageWithMagicCard() {

        }

        @Test
        void engageWithMagicCardAttackOnSelf() {

        }

        @Test
        void engageWithMagicCardHealOnOpponent() {

        }

        @Test
        void engageWithMagicCardWrongMagicType() {
            // Wrong == MagicType != ATTACK_PLAYER || HEAL_PLAYER
        }

    }

    @Nested
    @DisplayName("TESTS engage Monster")
    class Monster {

        MonsterCard monster = new MonsterCard(100, "Monster", 5, 5, 5, 5, new BuffCard(0, 0, EffectType.NONE));
        @BeforeEach
        void setup() {
            players[roundCounter.getOpponentIndex()].heal(999);
            players[roundCounter.getTurn()].heal(999);
            assertTrue(players[roundCounter.getOpponentIndex()].isAlive());
            assertTrue(players[roundCounter.getTurn()].isAlive());
        }

        @Test
        void engageOpponentDead() {
            players[roundCounter.getOpponentIndex()].addDamage(19);
            players[roundCounter.getTurn()].addDamage(19);

            assertTrue(gameEngine.engage(monster));
            assertFalse(players[roundCounter.getOpponentIndex()].isAlive());
            assertTrue(players[roundCounter.getTurn()].isAlive());
        }

        @Test
        void engageOpponentDead2() {
            players[roundCounter.getOpponentIndex()].addDamage(17);
            players[roundCounter.getTurn()].addDamage(17);
            assertTrue(gameEngine.engage(monster));

            assertFalse(players[roundCounter.getOpponentIndex()].isAlive());
            assertTrue(players[roundCounter.getTurn()].isAlive());
        }

        @Test
        void engageOpponentAlive() {
            players[roundCounter.getOpponentIndex()].addDamage(16);
            players[roundCounter.getTurn()].addDamage(16);
            gameEngine.engage(monster);

            assertTrue(players[roundCounter.getOpponentIndex()].isAlive());
            assertTrue(players[roundCounter.getTurn()].isAlive());
        }
    }




    @Nested
    @DisplayName("TESTS engage Monster vs Monster")
    class MonsterVsMonster {

        MonsterCard monsterBest;
        MonsterCard monsterWorst;

        @BeforeEach
        void setup() {
            monsterBest = new MonsterCard(1, "BEST", 1, 8, 7, 7, new BuffCard(0, 100, EffectType.ATTACK));
            monsterWorst = new MonsterCard(2, "WORST", 1, 3, 2, 2, new BuffCard(0, 0, EffectType.NONE));
        }

        @Test
        void engageMonsterVsMonster() {
            MonsterCard[] cards = gameEngine.engage(monsterWorst, monsterBest);
            assertNotNull(cards[0]);
            assertNull(cards[1]);
            assertEquals(2, cards.length);
        }


        @Test
        void engageMonsterVsMonsterAttackerWin() {
            MonsterCard[] cards = gameEngine.engage(monsterWorst, monsterBest);
            assertNotNull(cards[0]);
            assertNull(cards[1]);
            assertEquals(2, cards.length);
        }

        @Test
        void engageMonsterVsMonsterDefenderWin() {
            MonsterCard[] cards = gameEngine.engage(monsterWorst, monsterBest);
            assertNotNull(cards[1]);
            assertNull(cards[0]);
            assertEquals(2, cards.length);
        }

        @Test
        void engageMonsterVsMonsterNullIn() {
            assertNull(gameEngine.engage(monsterBest, null));
            assertNull(gameEngine.engage(null, monsterBest));
            assertNull(gameEngine.engage((MonsterCard) null, null));
        }

        @Test
        void engageMonsterVsMonsterSameCardIn() {
            assertNull(gameEngine.engage(monsterBest, monsterBest));
        }
    }

    @Nested
    @DisplayName("TESTS engage Magic Non Targeted")
    class MagicNonTargeted {

        @Test
        void engageMagicNull(){

        }

        @Test
        void engageMagicNonTargetedNoTargets() {

        }

        @Test
        void engageMagicNonTargetedStun() {

        }

        @Test
        void engageMagicNonTargetedHealPlayer() {

        }

        @Test
        void engageMagicNonTargetedHealing() {

        }

        @Test
        void engageMagicNonTargetedAttack() {

        }

        @Test
        void engageMagicNonTargetedAttackPlayer() {

        }

        @Test
        void engageMagicNonTargetedRemoveBuff() {

        }

        @Test
        void engageMagicNonTargetedRemoveDebuff() {

        }

    }
}
