package com.company.tests.cards;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MonsterCardTest {

    MonsterCard monsterCard;

    @BeforeEach
    void resetMonsterCard() {
        monsterCard = null;
    }

    @Test
    void constructorTestBonusNull() {
        try {
            new MonsterCard(0, "", 0, 0, 0, 0, null);
        } catch (Exception e) {
            fail("Failed to initialize with bonus as null");
        }
    }
    @Test
    void constructorTestBonusNotNull() {
        try {
            new MonsterCard(0, "", 0, 0, 0, 0, new BuffCard(0, "", 0, EffectType.ATTACK));
        } catch (Exception e) {
            fail("Failed to initialize with BuffCard as bonus");
        }
    }




    @Nested
    @DisplayName("Tests with MonsterCard created")
    class MonsterCardCreated {

        MonsterCard monsterCardWithBonus;
        MonsterCard monsterCardWithoutBonus;

        @BeforeEach
        void setUp() {
            monsterCardWithBonus = new MonsterCard(1, "Hunter", 2, 2, 2, 2, null);
            monsterCardWithoutBonus = new MonsterCard(1, "Hunter", 2, 2, 2, 2, null);
        }

        @Test
        void getStamina() {
        }

        @Test
        void getHp() {
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
