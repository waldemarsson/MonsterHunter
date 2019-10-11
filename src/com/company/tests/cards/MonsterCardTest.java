package com.company.tests.cards;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class MonsterCardTest {

    MonsterCard monsterCard;

    @BeforeEach
    void resetMonsterCard() {
        monsterCard = null;
    }

    @Test
    void constructorTest() {
        try {
            new MonsterCard(0, "", 1, 1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize MonsterCard");
        }
    }

    @Test
    void constructorTestStaminaNeg() {
        try {
            new MonsterCard(0, "", -1, 1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with stamina as negative value");
        }
    }

    @Test
    void constructorTestHpNeg() {
        try {
            new MonsterCard(0, "", 1, -1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with hp as negative value");
        }
    }

    @Test
    void constructorTestAttackNeg() {
        try {
            new MonsterCard(0, "", 1, 1, -1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with attack as negative value");
        }
    }

    @Test
    void constructorTestDefenseNeg() {
        try {
            new MonsterCard(0, "", 1, 1, 1, -1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with defense as negative value");
        }
    }

    @Test
    void constructorTestBonusNull() {
        try {
            new MonsterCard(0, "", 1, 1, 1, 1, null);
        } catch (Exception e) {
            fail("Failed to initialize with bonus as null");
        }
    }


    @Nested
    @DisplayName("Tests with MonsterCard created")
    class MonsterCardCreated {

        MonsterCard monsterCardWithBonusStatsAs4AndBonusAsNone;
        MonsterCard monsterCardWithBonusStatsAs8AndBonusSet;

        @BeforeEach
        void setUp() {
            monsterCardWithBonusStatsAs4AndBonusAsNone = new MonsterCard(1, "Hunter", 4, 4, 4, 4, new BuffCard(0, "", 0, EffectType.NONE));
            monsterCardWithBonusStatsAs8AndBonusSet = new MonsterCard(1, "Hunter", 8, 8, 8, 8, new BuffCard(0, "", 0, EffectType.HEALTH));
        }

        @Test
        void getCalculatedStamina() {
            assertEquals(1, monsterCardWithBonusStatsAs4AndBonusAsNone.getCalculatedStamina(), "Stamina was not equal");
            assertEquals(1, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedStamina(), "Stamina was not equal");
        }

        @Test
        void getHp() {
            assertEquals(4, monsterCardWithBonusStatsAs4AndBonusAsNone.getHp(), "Hp was not equal");
            assertEquals(8, monsterCardWithBonusStatsAs8AndBonusSet.getHp(), "Hp was not equal");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage1() {
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(7, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage10() {
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(10);
            assertEquals(-2, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfter5AddedDamage1() {
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(7, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(6, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(5, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(4, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            monsterCardWithBonusStatsAs8AndBonusSet.addDamage(1);
            assertEquals(3, monsterCardWithBonusStatsAs8AndBonusSet.getCalculatedHealth(), "Hp after 5 addDamage(1)");
        }


        @Test
        void isAlive(){
            assertEquals(true, monsterCardWithBonusStatsAs4AndBonusAsNone.isAlive(), "Should be dead");
        }


        @Test
        void getCalculatedAttack() {
            assertEquals(4, monsterCardWithBonusStatsAs4AndBonusAsNone.getCalculatedAttack(), "Failed");

        }

        @Test
        void getDefense() {
        }

        @Test
        void getDebuffCard() {
        }

        @Test
        void getBuffCard() {
        }

        @Test
        void getBonus() {
        }

        @Test
        void addDamage() {
        }

        @Test
        void addOneToFatigue() {
        }

        @Test
        void setDebuffCard() {
        }

        @Test
        void setBuffCard() {
        }

        @Test
        void testToString() {
        }
    }
}
