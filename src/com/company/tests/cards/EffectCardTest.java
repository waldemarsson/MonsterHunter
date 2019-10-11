package com.company.tests.cards;

import com.company.game.cards.EffectCard;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectCardTest {

    /**
     * Implementation of abstract class needed for testing
     */
    class EffectCardSuperTest extends EffectCard {
        public EffectCardSuperTest(int id, int value, EffectType effectType) {
            super(id, value, effectType);
        }
    }

    @Test
    void standardConstructorTest(){
        try{
            new EffectCardSuperTest(0, 2, EffectType.NONE);
        } catch (Exception E){
            fail("Constructor failed");
        }
    }

    @Test
    void belowZeroValue(){
        assertEquals(0, new EffectCardSuperTest(2, -1, EffectType.STAMINA).getValue(),
                "Failed to handle negative value of effect card");
    }

    @Test
    void zeroIdForNoneEffect(){
        assertEquals(0, new EffectCardSuperTest(2, 0, EffectType.NONE).getId(),
                "Failed to assign a NONE effect card with id 0");
    }


    @Test
    void getStaminaEffect() {
        assertEquals(2, new EffectCardSuperTest(2, 2, EffectType.STAMINA).getStaminaEffect(),
                "Failed to assert stamina");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.ATTACK).getStaminaEffect(),
                "Attack effected stamina effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.NONE).getStaminaEffect(),
                "None effected stamina effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.DEFENSE).getStaminaEffect(),
                "Defense effected stamina effect");
    }

    @Test
    void getAttackEffect() {
        assertEquals(2, new EffectCardSuperTest(2, 2, EffectType.ATTACK).getAttackEffect(),
                "Failed to assert stamina");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.STAMINA).getAttackEffect(),
                "Stamina effected attack effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.NONE).getAttackEffect(),
                "None effected attack effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.DEFENSE).getAttackEffect(),
                "Defense effected attack effect");
    }

    @Test
    void getDefenseEffect() {
        assertEquals(2, new EffectCardSuperTest(2,  2, EffectType.DEFENSE).getDefenseEffect(),
                "Failed to assert defense");
        assertEquals(0, new EffectCardSuperTest(2,  2, EffectType.ATTACK).getDefenseEffect(),
                "Attack effected defense effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.NONE).getDefenseEffect(),
                "None effected defense effect");
        assertEquals(0, new EffectCardSuperTest(2, 2, EffectType.STAMINA).getDefenseEffect(),
                "Stamina effected defense effect");
    }

    @Test
    void testToString() {
    }
}