package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
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

//        MonsterCard monster =
        @BeforeEach
        void setup() {

        }
        @Test
        void engageWithMonsterRightTookDamage() {

        }
    }




    @Nested
    @DisplayName("TESTS engage Monster vs Monster")
    class MonsterVsMonster {

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
