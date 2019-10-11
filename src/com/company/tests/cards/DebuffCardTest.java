package com.company.tests.cards;

import com.company.game.cards.DebuffCard;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DebuffCardTest {

    @Test
    void getStaminaEffect() {
        assertEquals(-1, new DebuffCard(2, 1, EffectType.STAMINA).getStaminaEffect());
    }

    @Test
    void getAttackEffect() {
        assertEquals(-2, new DebuffCard(4, 2, EffectType.ATTACK).getAttackEffect());
    }

    @Test
    void getDefenseEffect() {
        assertEquals(-1, new DebuffCard(9, 1, EffectType.DEFENSE).getDefenseEffect());
    }

    @Test
    void valueIsInvert(){
        assertEquals(-1, new DebuffCard(1, 1, EffectType.STAMINA).getValue());
    }

    @Test
    void testToString() {
        assertEquals("STAMINA_2: -1", new DebuffCard(2, 1, EffectType.STAMINA).toString());
    }
}