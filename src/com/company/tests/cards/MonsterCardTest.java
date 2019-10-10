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

    @Disabled
    @Test
    void constructorTestIdNeg() {
        try {
            new MonsterCard(-1, "", 1, 1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with id as negative value");
        }
    }

    @Disabled
    @Test
    void constructorTestNameNull() {
        try {
            new MonsterCard(0, null, 1, 1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with name as null");
        }
    }

    @Disabled
    @Test
    void constructorTestStaminaNeg() {
        try {
            new MonsterCard(0, "", -1, 1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with stamina as negative value");
        }
    }

    @Disabled
    @Test
    void constructorTestHpNeg() {
        try {
            new MonsterCard(0, "", 1, -1, 1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with hp as negative value");
        }
    }

    @Disabled
    @Test
    void constructorTestAttackNeg() {
        try {
            new MonsterCard(0, "", 1, 1, -1, 1, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with attack as negative value");
        }
    }

    @Disabled
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

    @Test
    void constructorTestNameSomeString1() {
        new MonsterCard(0, "oadsuasdq", 1, 1, 1, 1, null);
    }

    @Test
    void constructorTestNameSomeString2() {
        new MonsterCard(0, "HUNTER_AS", 1, 1, 1, 1, null);
    }

    @Test
    void constructorTestNameSomeString3() {
        new MonsterCard(0, "812e0d", 1, 1, 1, 1, null);
    }

    @Test
    void constructorTestNameSomeString4() {
        new MonsterCard(0, "d1womi19j87y1v7e78141b41bnjiqsdjknDSJAHBSDB", 1, 1, 1, 1, null);
    }

    @Test
    void constructorTestNameSomeString5() {
        new MonsterCard(0, "ÖÄ*ÅÅ^^ÖÅ´å1+01+20´¨ö'ö", 1, 1, 1, 1, null);
    }


    @Nested
    @DisplayName("Tests with MonsterCard created")
    class MonsterCardCreated {

        MonsterCard monsterCardWithBonusStatsAs2AndBonusSet;
        MonsterCard monsterCardWithBonusStatsAs4AndBonusAsNull;

        @BeforeEach
        void setUp() {
            monsterCardWithBonusStatsAs2AndBonusSet = new MonsterCard(1, "Hunter", 2, 2, 2, 2, new BuffCard(0, "", 0, EffectType.ATTACK));
            monsterCardWithBonusStatsAs4AndBonusAsNull = new MonsterCard(1, "Hunter", 4, 4, 4, 4, null);
        }

        @Test
        void getStamina() {
            assertEquals(2, monsterCardWithBonusStatsAs2AndBonusSet.getStamina(), "Stamina was not equal");
            assertEquals(4, monsterCardWithBonusStatsAs4AndBonusAsNull.getStamina(), "Stamina was not equal");
        }

        @Test
        void getHp() {
            assertEquals(2, monsterCardWithBonusStatsAs2AndBonusSet.getHp(), "Hp was not equal");
            assertEquals(4, monsterCardWithBonusStatsAs4AndBonusAsNull.getHp(), "Hp was not equal");
        }

        @Test
        void getDamage() {
        }

        @Test
        void getAttack() {
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
