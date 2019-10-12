package com.company.tests.cards;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardTest {

    @Test
    void testToString1() {
        String str = "FIREBALL_1: VAL 4";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString2() {
        String str = "STUN_BOMB_1: VAL 2";
        MagicCard m = new MagicCard(1, MagicType.STUN, false, 2);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString3() {
        String str = "HEALING_65: VAL 12";
        MagicCard m = new MagicCard(65, MagicType.HEAL_CARD, true, 12);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString4() {
        String str = "SPA_TREATMENT_23";
        MagicCard m = new MagicCard(23, MagicType.HEAL_PLAYER, true, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString5() {
        String str = "CRITIQUE_BOMB_23";
        MagicCard m = new MagicCard(23, MagicType.REMOVE_BUFF, false, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString6() {
        String str = "ADULT_BULLYING_99: VAL 8";
        MagicCard m = new MagicCard(99, MagicType.ATTACK_PLAYER, true, 8);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString7() {
        String str = "CRITIQUE_1";
        MagicCard m = new MagicCard(1, MagicType.REMOVE_BUFF, true, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString8() {
        String str = "FIREBALL_1: VAL 4";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }
}