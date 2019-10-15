package com.company.tests;

import com.company.game.Board;
import com.company.game.GameEngine;
import com.company.game.RoundCounter;
import com.company.game.cards.*;
import com.company.game.collections.Hand;
import com.company.game.enums.EffectType;
import com.company.game.enums.MagicType;
import com.company.game.factories.DeckFactory;
import com.company.game.factories.EffectCardFactory;
import com.company.game.factories.MagicCardFactory;
import com.company.game.factories.MonsterCardFactory;
import com.company.game.players.Player;
import org.junit.jupiter.api.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    DeckFactory deckFactory = new DeckFactory();
    RoundCounter roundCounter;
    Board board;
    Player[] players;
    MonsterCardFactory monsterCardFactory = new MonsterCardFactory();
    MagicCardFactory magicCardFactory = new MagicCardFactory();
    List<MonsterCard>[] monsterPiles;


    void addFatigueToAllMonstersInPile(List<MonsterCard> monsters) {
        for (MonsterCard card : monsters) {
            card.addOneToFatigue();
        }
    }

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

    @Nested
    @DisplayName("TESTS placeEffectOnMonsterWithId")
    class placeEffectOnMonsterWithId {
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
    }

    @Nested
    @DisplayName("TESTS attackPlayerWithMonster")
    class AttackPlayerWithMonster {

        @BeforeEach
        void setup() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(2));
            roundCounter.nextTurn();

        }

        @Test
        void attackMonsterCardExists() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            assertTrue(board.attackPlayerWithMonster(1));
        }

        @Test
        void attackMonsterCardDoesNotExists() {
            assertFalse(board.attackPlayerWithMonster(99));
        }

        @Test
        void attackMonsterCardIsNotMine() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            roundCounter.nextTurn();
            board.attackPlayerWithMonster(1);
        }

        @Test
        void attackMonsterCardIsNegative() {
            assertFalse(board.attackPlayerWithMonster(-1));
        }

        @Test
        void attackPlayerMonstersOnBoard() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(2));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(3));
            addFatigueToAllMonstersInPile(monsterPiles[roundCounter.getTurn()]);
            addFatigueToAllMonstersInPile(monsterPiles[roundCounter.getTurn()]);

            assertFalse(board.attackPlayerWithMonster(3));
        }

    }

    @Nested
    @DisplayName("TESTS attackMonsterWithMonster")
    class MonsterVsMonster {
        @Test
        void attackMonsterWithMonster() {
            //Player 1 turn: places card
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            roundCounter.nextTurn();


            //Player 2 turn: places card
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(2));
            roundCounter.nextTurn();

            //Player 1 attacks Player 2
            assertTrue(board.attackMonsterWithMonster(2, 1));
            assertFalse(board.attackMonsterWithMonster(11, 1));
            assertFalse(board.attackMonsterWithMonster(10, 2));

            //Switch turn to Player 2
            roundCounter.nextTurn();

            //Player 2 attacks Player 1
            assertTrue(board.attackMonsterWithMonster(1, 2));
            assertFalse(board.attackMonsterWithMonster(1, 10));
            assertFalse(board.attackMonsterWithMonster(10, 2));
        }

        // Disabled for now, waiting for logic in gameEngine.engage to return list with surviving mosters
        @Disabled
        @Test
        void attackMonsterVsMonsterAttackerDied() {
            board.placeMonsterOnBoard(new MonsterCard(2, "Target", 1, 100, 100, 100, new BuffCard(0, 0, EffectType.NONE)));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(new MonsterCard(1, "Attacker", 1, 1, 1, 1, new BuffCard(0, 0, EffectType.NONE)));
            assertEquals(1, monsterPiles[roundCounter.getOpponentIndex()].size());
            assertEquals(1, monsterPiles[roundCounter.getTurn()].size());
            assertTrue(board.attackMonsterWithMonster(2, 1));
            assertEquals(1, monsterPiles[roundCounter.getOpponentIndex()].size());
            assertEquals(0, monsterPiles[roundCounter.getTurn()].size());
        }

        // Disabled for now, waiting for logic in gameEngine.engage to return list with surviving mosters
        @Disabled
        @Test
        void attackMonsterVsMonsterTargetDied() {
            board.placeMonsterOnBoard(new MonsterCard(1, "Target", 1, 1, 1, 1, new BuffCard(0, 0, EffectType.NONE)));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(new MonsterCard(2, "Attacker", 1, 100, 100, 100, new BuffCard(0, 0, EffectType.NONE)));
            assertEquals(1, monsterPiles[roundCounter.getOpponentIndex()].size());
            assertEquals(1, monsterPiles[roundCounter.getTurn()].size());
            assertTrue(board.attackMonsterWithMonster(1, 2));
            assertEquals(0, monsterPiles[roundCounter.getOpponentIndex()].size());
            assertEquals(1, monsterPiles[roundCounter.getTurn()].size());
        }

        @Test
        void attackMonsterVsMonsterAttackerStamina0() {
            board.placeMonsterOnBoard(new MonsterCard(1, "Target", 1, 1, 1, 1, new BuffCard(0, 0, EffectType.NONE)));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(new MonsterCard(2, "Attacker", 0, 100, 100, 100, new BuffCard(0, 0, EffectType.NONE)));
            assertFalse(board.attackMonsterWithMonster(2, 1));
        }
    }

    @Nested
    @DisplayName("TESTS useMagicOnMonster")
    class useMagicOnMonster {

        @Test
        void useMagicOnMonsterOwnMonster() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            MagicCard magicCard = new MagicCard(2, MagicType.HEAL_CARD, true, 2);

            assertTrue(board.useMagicOnMonster(magicCard, 1));
            assertFalse(board.useMagicOnMonster(magicCard, 10));
        }

        @Test
        void useMagicOnMonsterOpponentMonster() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            roundCounter.nextTurn();
            MagicCard magicCard = new MagicCard(2, MagicType.ATTACK_CARD, true, 2);

            assertTrue(board.useMagicOnMonster(magicCard, 1));
            assertFalse(board.useMagicOnMonster(magicCard, 10));
        }

        @Test
        void useMagicOnMonsterNullValues() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));

            assertFalse(board.useMagicOnMonster(null, 1));
            assertFalse(board.useMagicOnMonster(null, 10));
            assertFalse(board.useMagicOnMonster(null, 1));
            assertFalse(board.useMagicOnMonster(null, 10));
        }

        @Test
        void useNonTargetedOnOwnMonster() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            MagicCard magicCard = new MagicCard(2, MagicType.HEAL_CARD, false, 2);

            assertFalse(board.useMagicOnMonster(magicCard, 1));
            assertFalse(board.useMagicOnMonster(magicCard, 10));
        }

        @Test
        void useNonTargetedOnOpponentMonster() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            MagicCard magicCard = new MagicCard(2, MagicType.ATTACK_CARD, false, 2);
            roundCounter.nextTurn();

            assertFalse(board.useMagicOnMonster(magicCard, 1));
            assertFalse(board.useMagicOnMonster(magicCard, 10));
        }


        @Test
        void useMagicTypePlayerOnMonster() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));

            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.ATTACK_PLAYER, true, 2), 1));
            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.HEAL_PLAYER, true, 2), 1));
        }

        @Test
        void useNegativeMagicTypesOnSelf() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));

            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.ATTACK_CARD, true, 2), 1));
            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.STUN, true, 2), 1));
            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.REMOVE_BUFF, true, 2), 1));
        }

        @Test
        void usePositiveMagicTypesOnOpponent() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            roundCounter.nextTurn();

            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.HEAL_CARD, true, 2), 1));
            assertFalse(board.useMagicOnMonster(new MagicCard(2, MagicType.REMOVE_DEBUFF, true, 2), 1));
        }
    }


    @Nested
    @DisplayName("TESTS useMagic")
    class useMagic {


        @BeforeEach
        void setup() {
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(1));
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(2));
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(3));
            roundCounter.nextTurn();
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(4));
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(5));
            board.placeMonsterOnBoard(monsterCardFactory.buildCard(6));
            roundCounter.nextTurn();
        }

        @Test
        void magicNull() {
            assertFalse(board.useMagic(null));
        }

        @Test
        void usePositiveMagic() {
            assertFalse(board.useMagic(new MagicCard(10, MagicType.HEAL_CARD, true, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.HEAL_CARD, false, 1)));
            assertFalse(board.useMagic(new MagicCard(10, MagicType.REMOVE_DEBUFF, true, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.REMOVE_DEBUFF, false, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.HEAL_PLAYER, true, 1)));
        }

        @Test
        void useNegativeMagic() {
            assertFalse(board.useMagic(new MagicCard(10, MagicType.STUN, true, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.STUN, false, 1)));
            assertFalse(board.useMagic(new MagicCard(10, MagicType.ATTACK_CARD, true, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.ATTACK_CARD, false, 1)));
            assertFalse(board.useMagic(new MagicCard(10, MagicType.REMOVE_BUFF, true, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.REMOVE_BUFF, false, 1)));
            assertTrue(board.useMagic(new MagicCard(10, MagicType.ATTACK_PLAYER, true, 1)));
        }
    }

    @Nested
    @DisplayName("TESTS nextRound")
    class NextRound {


        @BeforeEach
        void getRoundCounterFromBoard() {

        }

        @Test
        void didCounterIncrease() {
            int counter = roundCounter.getTurn();
            board.nextRound();
            assertEquals(counter + 1, roundCounter.getTurn());
        }

        @Test
        void didCounterIncreaseLoop() {
            for (int i = roundCounter.getTurn(); i < 1000; i++) {
                assertEquals(i % 2, roundCounter.getTurn());
                board.nextRound();
                assertEquals((i + 1) % 2, roundCounter.getTurn());
            }

        }

        @Nested
        @DisplayName("TESTS resetFatigue")
        class Fatigue {

            @BeforeEach
            void addFatigueToMonsters() {
                addFatigueToAllMonstersInPile((List<MonsterCard>) monsterPiles[roundCounter.getTurn()]);
                addFatigueToAllMonstersInPile((List<MonsterCard>) monsterPiles[roundCounter.getOpponentIndex()]);

            }

            @Test
            void resetFatigue() {
                board.nextRound();
                for (MonsterCard card : (List<MonsterCard>) monsterPiles[roundCounter.getOpponentIndex()]) {
                    assertEquals(0, card.getFatigue());
                }
                addFatigueToAllMonstersInPile((List<MonsterCard>) monsterPiles[roundCounter.getTurn()]);

                board.nextRound();
                for (MonsterCard card : (List<MonsterCard>) monsterPiles[roundCounter.getOpponentIndex()]) {
                    assertEquals(0, card.getFatigue());
                }
                addFatigueToAllMonstersInPile((List<MonsterCard>) monsterPiles[roundCounter.getTurn()]);

                board.nextRound();
                for (MonsterCard card : (List<MonsterCard>) monsterPiles[roundCounter.getOpponentIndex()]) {
                    assertEquals(0, card.getFatigue());
                }
            }
        }
    }


}
