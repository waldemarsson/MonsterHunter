package com.company.tests.cards;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardTest {

    @Test
    void getMagicType() {
    }

    @Test
    void isTargeted() {
    }

    @Test
    void getValue() {
    }

    @Test
    void testToString1() {
        String str = "FIREBALL_1: VAL 4";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString2() {
        String str = "STUNBOMB_1: VAL 2";
        MagicCard m = new MagicCard(1, MagicType.STUN, true, 4);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString3() {
        String str = "FIREBALL_1: VAL 4 TARG ONE";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString4() {
        String str = "FIREBALL_1: VAL 4 TARG ONE";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }
}