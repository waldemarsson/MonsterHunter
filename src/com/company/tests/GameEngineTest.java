package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.cards.BuffCard;
import com.company.game.cards.DebuffCard;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import com.company.game.enums.MagicType;
import com.company.game.factories.DeckFactory;
import com.company.game.factories.EffectCardFactory;
import com.company.game.factories.MagicCardFactory;
import com.company.game.factories.MonsterCardFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    void engageMagicCardNullAndNull() {
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
    @DisplayName("TESTS engage Magic Non Targeted")
    class MagicNonTargeted {

        List<MonsterCard> monsterCards;

        @BeforeEach
        void setup() {
            for(int i = 0; i < 10; i++) {
                monsterCards.add(monsterCardFactory.buildCard(i +1));
            }
        }

        @Nested
        @DisplayName("TESTS misc")
        class Misc {
            @Test
            void engageMagicNull() {
                assertFalse(gameEngine.engage((MagicCard) null));
            }

            @Test
            void engageMagicNonTargetedAttack() {
                assertTrue(gameEngine.engage(new MagicCard(501, MagicType.ATTACK_CARD, false, 2)));
            }
        }

        @Test
        void engageMagicNonTargetedAttackPlayer() {
            MagicCard card = new MagicCard(100, MagicType.ATTACK_PLAYER, false, 2);
            assertFalse(gameEngine.engage(card));
        }

        @Test
        void engageMagicTargetedAttackPlayer() {
            MagicCard card = new MagicCard(100, MagicType.ATTACK_PLAYER, true, 2);
            assertTrue(gameEngine.engage(card));
        }

        @Test
        void engageMagicNonTargetedNoTargets() {
            MagicCard card = new MagicCard(100, MagicType.ATTACK_CARD, false, 2);
            assertEquals(0, gameEngine.engage(card, new ArrayList<>()).size());
        }

        @Test
        void engageMagicTargetedNoTargets() {
            MagicCard card = new MagicCard(100, MagicType.ATTACK_CARD, true, 2);
            assertEquals(0, gameEngine.engage(card, new ArrayList<>()).size());
        }

        @Test
        void engageMagicNonTargetedStun() {
            MagicCard card = new MagicCard(100, MagicType.STUN, false, 2);
            int stunTotalBefore = monsterCards.stream().map(MonsterCard::getFatigue).mapToInt(Integer::intValue).sum();
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            int stunTotalAfter = monsterCards.stream().map(MonsterCard::getFatigue).mapToInt(Integer::intValue).sum();
            assertTrue(stunTotalAfter < stunTotalBefore);
        }

        @Test
        void engageMagicTargetedStun() {
            MagicCard card = new MagicCard(100, MagicType.STUN, true, 2);
            int stunTotalBefore = monsterCards.stream().map(MonsterCard::getFatigue).mapToInt(Integer::intValue).sum();
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            int stunTotalAfter = monsterCards.stream().map(MonsterCard::getFatigue).mapToInt(Integer::intValue).sum();
            assertTrue(stunTotalAfter == stunTotalBefore);
        }

        @Test
        void engageMagicNonTargetedHealPlayer() {
            MagicCard card = new MagicCard(100, MagicType.HEAL_PLAYER, false, 2);
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
        }

        @Test
        void engageMagicTargetedHealPlayer() {
            MagicCard card = new MagicCard(100, MagicType.HEAL_PLAYER, true, 2);
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
        }

        @Test
        void engageMagicNonTargetedHealing() {
            MagicCard card = new MagicCard(100, MagicType.HEAL_CARD, false, 2);
            monsterCards.forEach(monsterCard -> monsterCard.addDamage(2));
            int damageTotalBefore = monsterCards.stream().map(MonsterCard::getDamage).mapToInt(Integer::intValue).sum();
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            int damageTotalAfter = monsterCards.stream().map(MonsterCard::getDamage).mapToInt(Integer::intValue).sum();
            assertTrue(damageTotalAfter < damageTotalBefore);
        }

        @Test
        void engageMagicTargetedHealing() {
            MagicCard card = new MagicCard(100, MagicType.HEAL_CARD, true, 2);
            monsterCards.forEach(monsterCard -> monsterCard.addDamage(2));
            int damageTotalBefore = monsterCards.stream().map(MonsterCard::getDamage).mapToInt(Integer::intValue).sum();
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            int damageTotalAfter = monsterCards.stream().map(MonsterCard::getDamage).mapToInt(Integer::intValue).sum();
            assertTrue(damageTotalAfter == damageTotalBefore);
        }


        @Test
        void engageMagicNonTargetedRemoveBuff() {
            MagicCard card = new MagicCard(100, MagicType.REMOVE_BUFF, false, 2);
            monsterCards.forEach(monsterCard -> monsterCard.setBuffCard((BuffCard) new EffectCardFactory().buildEffectCard(20, true)));
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getBuffCard().getId() != 0).count());
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            assertEquals(monsterCards.size(), monsterCards.stream().filter(monsterCard -> monsterCard.getBuffCard().getId() != 0).count());
        }

        @Test
        void engageMagicTargetedRemoveBuff() {
            MagicCard card = new MagicCard(100, MagicType.REMOVE_BUFF, true, 2);
            monsterCards.forEach(monsterCard -> monsterCard.setBuffCard((BuffCard) new EffectCardFactory().buildEffectCard(20, true)));
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getBuffCard().getId() != 0).count());
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getBuffCard().getId() != 0).count());

        }

        @Test
        void engageMagicNonTargetedRemoveDebuff() {
            MagicCard card = new MagicCard(100, MagicType.REMOVE_DEBUFF, false, 2);
            monsterCards.forEach(monsterCard -> monsterCard.setDebuffCard((DebuffCard) new EffectCardFactory().buildEffectCard(20, false)));
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getDebuffCard().getId() != 0).count());
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            assertEquals(monsterCards.size(), monsterCards.stream().filter(monsterCard -> monsterCard.getDebuffCard().getId() != 0).count());
        }

        @Test
        void engageMagicTargetedRemoveDebuff() {
            MagicCard card = new MagicCard(100, MagicType.REMOVE_DEBUFF, true, 2);
            monsterCards.forEach(monsterCard -> monsterCard.setDebuffCard((DebuffCard) new EffectCardFactory().buildEffectCard(20, false)));
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getDebuffCard().getId() != 0).count());
            assertEquals(monsterCards.size(), gameEngine.engage(card, monsterCards).size());
            assertEquals(0, monsterCards.stream().filter(monsterCard -> monsterCard.getDebuffCard().getId() != 0).count());
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
            assertNotNull(cards[1]);
            assertNull(cards[0]);
            assertEquals(2, cards.length);
        }

        @Test
        void engageMonsterVsMonsterAttackerWin() {
            MonsterCard[] cards = gameEngine.engage(monsterWorst, monsterBest);
            assertNull(cards[0]);
            assertNotNull(cards[1]);
            assertEquals(2, cards.length);
        }

        @Test
        void engageMonsterVsMonsterTargetWin() {
            MonsterCard[] cards = gameEngine.engage(monsterBest, monsterWorst);
            assertNull(cards[1]);
            assertNotNull(cards[0]);
            assertEquals(2, cards.length);
        }

        @Test
        void engageMonsterVsMonsterNullIn() {
            MonsterCard[] cards = gameEngine.engage(monsterBest, null);
            assertNull(cards[0]);
            assertNull(cards[1]);

            cards = gameEngine.engage(null, monsterBest);
            assertNull(cards[0]);
            assertNull(cards[1]);

            cards = gameEngine.engage((MonsterCard) null, null);
            assertNull(cards[0]);
            assertNull(cards[1]);
        }

        @Test
        void engageMonsterVsMonsterSameCardIn() {
            MonsterCard[] cards = gameEngine.engage(monsterBest, monsterBest);
            assertNull(cards[0]);
            assertNull(cards[1]);
        }
    }
}