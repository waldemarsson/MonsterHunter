package com.company.tests.cards;

import com.company.game.cards.BuffCard;
import com.company.game.cards.Card;
import com.company.game.cards.DebuffCard;
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
            new MonsterCard(0, "", 1, 1, 1, 1, new BuffCard(0, 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize MonsterCard");
        }
    }

    @Test
    void constructorTestStaminaNeg() {
        try {
            new MonsterCard(0, "", -1, 1, 1, 1, new BuffCard(0, 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with stamina as negative value");
        }
    }

    @Test
    void constructorTestHpNeg() {
        try {
            new MonsterCard(0, "", 1, -1, 1, 1, new BuffCard(0, 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with hp as negative value");
        }
    }

    @Test
    void constructorTestAttackNeg() {
        try {
            new MonsterCard(0, "", 1, 1, -1, 1, new BuffCard(0, 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with attack as negative value");
        }
    }

    @Test
    void constructorTestDefenseNeg() {
        try {
            new MonsterCard(0, "", 1, 1, 1, -1, new BuffCard(0, 0, EffectType.ATTACK));
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
        MonsterCard cStats6Bonus4Attack;
        MonsterCard cStats6Bonus4Defense;
        MonsterCard cStats1Bonus2Stamina;

        @BeforeEach
        void setUp() {
            BuffCard buff = new BuffCard(0, 0, EffectType.NONE);
//            System.out.println(buff);
            cStats4BonusNone = new MonsterCard(1, "Hunter", 4, 4, 4, 4, new BuffCard(0, 0, EffectType.NONE));
            cStats6Bonus4Attack = new MonsterCard(1, "Hunter", 6, 6, 6, 6, new BuffCard(0, 0, EffectType.ATTACK));
            cStats6Bonus4Defense = new MonsterCard(1, "Hunter", 6, 6, 6, 6, new BuffCard(0, 0, EffectType.DEFENSE));
            cStats1Bonus2Stamina = new MonsterCard(1, "Hunter", 1, 1, 1, 1, new BuffCard(0, 0, EffectType.STAMINA));
        }

        @Test
        void fatigueIsMaxedOutAfterConstructorAndStaminaIsZero(){
            assertEquals(Integer.MAX_VALUE, cStats4BonusNone.getFatigue());
            assertEquals(0, cStats4BonusNone.getCalculatedStamina());
        }

        @Test
        void getCalculatedStaminaAfterReset() {
            cStats4BonusNone.resetFatigue();
            cStats6Bonus4Attack.resetFatigue();
            assertEquals(1, cStats4BonusNone.getCalculatedStamina(), "Stamina was not equal");
            assertEquals(1, cStats6Bonus4Attack.getCalculatedStamina(), "Stamina was not equal");
        }

        @Test
        void getHp() {
            assertEquals(4, cStats4BonusNone.getHp(), "Hp was not equal");
            assertEquals(6, cStats6Bonus4Attack.getHp(), "Hp was not equal");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage1() {
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(5, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfterAddedDamage10() {
            cStats6Bonus4Attack.addDamage(10);
            assertEquals(-4, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after addDamage(1)");
        }

        @Test
        void getCalculatedHealthAfter5AddedDamage1() {
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(5, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(4, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(3, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(2, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after 5 addDamage(1)");
            cStats6Bonus4Attack.addDamage(1);
            assertEquals(1, cStats6Bonus4Attack.getCalculatedHealth(), "Hp after 5 addDamage(1)");
        }


        @Test
        void isAlive() {
            assertEquals(true, cStats4BonusNone.isAlive(), "Should be dead");
        }


        @Test
        void getCalculatedAttack() {
            assertEquals(4, cStats4BonusNone.getCalculatedAttack(), "Failed");
            assertEquals(6, cStats6Bonus4Attack.getCalculatedAttack());
        }

        @Test
        void getCalculatedDefense() {
            assertEquals(4, cStats4BonusNone.getCalculatedDefense(), "Failed");
            assertEquals(6, cStats6Bonus4Defense.getCalculatedDefense());
        }

        @Test
        void getDebuffCard() {
            assertNotNull(cStats4BonusNone.getDebuffCard());
            assertNotNull(cStats6Bonus4Attack.getDebuffCard());
            assertNotNull(cStats6Bonus4Defense.getDebuffCard());
        }

        @Test
        void addDamage() {
            cStats4BonusNone.addDamage(1);
            assertEquals(1, cStats4BonusNone.getDamage());
            cStats4BonusNone.addDamage(2);
            assertEquals(3, cStats4BonusNone.getDamage());
            cStats4BonusNone.addDamage(1);
            assertEquals(4, cStats4BonusNone.getDamage());

            cStats4BonusNone.addDamage(-5);
            assertEquals(4, cStats4BonusNone.getDamage());
        }

        @Test
        void addOneToFatigue() {
            cStats4BonusNone.resetFatigue();
            cStats4BonusNone.addOneToFatigue();
            assertEquals(1, cStats4BonusNone.getFatigue());
            cStats4BonusNone.addOneToFatigue();
            assertEquals(2, cStats4BonusNone.getFatigue());
            cStats4BonusNone.addOneToFatigue();
            assertEquals(3, cStats4BonusNone.getFatigue());
            cStats4BonusNone.addOneToFatigue();
            assertEquals(4, cStats4BonusNone.getFatigue());
        }

        @Test
        void setDebuffCard() {
            DebuffCard initDebuffCard = cStats4BonusNone.getDebuffCard();
            cStats4BonusNone.setDebuffCard(new DebuffCard(1, 2, EffectType.ATTACK));
            assertNotSame(initDebuffCard, cStats4BonusNone.getDebuffCard());

            cStats4BonusNone.setDebuffCard(null);
            assertNotNull(cStats4BonusNone.getDebuffCard());
        }

        @Test
        void setBuffCard() {
            BuffCard initBuffCard = cStats4BonusNone.getBuffCard();
            cStats4BonusNone.setBuffCard(new BuffCard(1, 2, EffectType.ATTACK));
            assertNotSame(initBuffCard, cStats4BonusNone.getBuffCard());

            cStats4BonusNone.setBuffCard(null);
            assertNotNull(cStats4BonusNone.getBuffCard());
        }

        @Nested
        @DisplayName("TOSTRING TESTS")
        class toStringTests {

            MonsterCard card;
            String testString;

            @BeforeEach
            void setUp() {
                card = new MonsterCard(4, "RAT", 1, 4, 4, 4, new BuffCard(0, 0, EffectType.NONE));
                testString = null;
            }

            @Test
            void test1() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 4 DEF 4";
                assertEquals(testString, card.toString());
            }

            @Test
            void test2() {
                card = new MonsterCard(4, "RAT", 1, 4, 2, 4, new BuffCard(0, 0, EffectType.NONE));
                testString = "RAT_4: HP 4/4 STA 1 ATT 2 DEF 4";
                assertEquals(testString, card.toString());
            }

            @Test
            void test3(){
                card = new MonsterCard(4, "RAT", 1, 4, 2, 4, new BuffCard(0, 2, EffectType.ATTACK));
                testString = "EPIC_RAT_4: HP 4/4 STA 1 ATT 4 DEF 4";
                assertEquals(testString, card.toString());
            }

            @Test
            void test4() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 6(+2) DEF 4";
                card.setBuffCard(new BuffCard(5, 2, EffectType.ATTACK));
                assertEquals(testString, card.toString());

            }

            @Test
            void test5() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 4 DEF 7(+3)";
                card.setBuffCard(new BuffCard(5, 3, EffectType.DEFENSE));
                assertEquals(testString, card.toString());

            }

            @Test
            void test6() {
                testString = "RAT_4: HP 4/4 STA 2(+1) ATT 4 DEF 4";
                card.setBuffCard(new BuffCard(5, 1, EffectType.STAMINA));
                assertEquals(testString, card.toString());

            }

            @Test
            void test7() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 2(-2) DEF 4";
                card.setDebuffCard(new DebuffCard(5, 2, EffectType.ATTACK));
                assertEquals(testString, card.toString());

            }

            @Test
            void test8() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 4 DEF 1(-3)";
                card.setDebuffCard(new DebuffCard(5, 3, EffectType.DEFENSE));
                assertEquals(testString, card.toString());

            }

            @Test
            void test9() {
                testString = "RAT_4: HP 4/4 STA 0(-1) ATT 4 DEF 4";
                card.setDebuffCard(new DebuffCard(5, 1, EffectType.STAMINA));
                assertEquals(testString, card.toString());
            }

            @Test
            void test10() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 6(+2) DEF 1(-3)";
                card.setBuffCard(new BuffCard(5, 2, EffectType.ATTACK));
                card.setDebuffCard(new DebuffCard(6, 3, EffectType.DEFENSE));
                assertEquals(testString, card.toString());

            }

            @Test
            void test11() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 6(+3)(-1) DEF 4";
                card.setBuffCard(new BuffCard(5, 3, EffectType.ATTACK));
                card.setDebuffCard(new DebuffCard(9, 1, EffectType.ATTACK));
                assertEquals(testString, card.toString());

            }

            @Test
            void test12() {
                testString = "RAT_4: HP 4/4 STA 1 ATT 4 DEF 6(+3)(-1)";
                card.setBuffCard(new BuffCard(5, 3, EffectType.DEFENSE));
                card.setDebuffCard(new DebuffCard(9, 1, EffectType.DEFENSE));
                assertEquals(testString, card.toString());
            }


        }
    }
}
