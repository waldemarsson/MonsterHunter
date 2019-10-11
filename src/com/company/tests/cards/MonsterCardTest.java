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

        MonsterCard cStats4BonusNone;
        MonsterCard cStats6Bonus4Health;
        MonsterCard cStats6Bonus4Attack;
        MonsterCard cStats6Bonus4Defense;
        MonsterCard cStats1Bonus2Stamina;

        @BeforeEach
        void setUp() {
            cStats4BonusNone = new MonsterCard(1, "Hunter", 4, 4, 4, 4, new BuffCard(0, "", 0, EffectType.NONE));
            cStats6Bonus4Health = new MonsterCard(1, "Hunter", 6, 6, 6, 6, new BuffCard(0, "", 4, EffectType.HEALTH));
            cStats6Bonus4Attack = new MonsterCard(1, "Hunter", 6, 6, 6, 6, new BuffCard(0, "", 4, EffectType.ATTACK));
            cStats6Bonus4Defense = new MonsterCard(1, "Hunter", 6, 6, 6, 6, new BuffCard(0, "", 4, EffectType.DEFENSE));
            cStats1Bonus2Stamina = new MonsterCard(1, "Hunter", 1, 1, 1, 1, new BuffCard(0, "", 2, EffectType.STAMINA));
        }

        @Test
        void getCalculatedStamina() {
            assertEquals(1, cStats4BonusNone.getCalculatedStamina(), "Stamina was not equal");
            assertEquals(1, cStats6Bonus4Health.getCalculatedStamina(), "Stamina was not equal");
        }

        @Test
        void getHp() {
            assertEquals(4, cStats4BonusNone.getHp(), "Hp was not equal");
            assertEquals(6, cStats6Bonus4Health.getHp(), "Hp was not equal");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage1() {
            cStats6Bonus4Health.addDamage(1);
            assertEquals(5, cStats6Bonus4Health.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage10() {
            cStats6Bonus4Health.addDamage(10);
            assertEquals(-4, cStats6Bonus4Health.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfter5AddedDamage1() {
            cStats6Bonus4Health.addDamage(1);
            assertEquals(5, cStats6Bonus4Health.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Health.addDamage(1);
            assertEquals(4, cStats6Bonus4Health.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Health.addDamage(1);
            assertEquals(3, cStats6Bonus4Health.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Health.addDamage(1);
            assertEquals(2, cStats6Bonus4Health.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Health.addDamage(1);
            assertEquals(1, cStats6Bonus4Health.getCalculatedHealth(), "Hp after 5 addDamage(1)");
        }


        @Test
        void isAlive(){
            assertEquals(true, cStats4BonusNone.isAlive(), "Should be dead");
        }


        @Test
        void getCalculatedAttack() {
            assertEquals(4, cStats4BonusNone.getCalculatedAttack(), "Failed");
            assertEquals(6, cStats6Bonus4Attack.getCalculatedAttack());
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
