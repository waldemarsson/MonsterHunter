package com.company.tests.cards;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardTest {

    @Test
    void testToString1() {
        String str = "1_FIREBALL:              VAL  4";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString2() {
        String str = "1_STUN_BOMB:             VAL  2";
        MagicCard m = new MagicCard(1, MagicType.STUN, false, 2);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString3() {
        String str = "65_HEALING:              VAL  12";
        MagicCard m = new MagicCard(65, MagicType.HEAL_CARD, true, 12);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString4() {
        String str = "23_SPA_TREATMENT         ";
        MagicCard m = new MagicCard(23, MagicType.HEAL_PLAYER, true, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString5() {
        String str = "23_CRITIQUE_BOMB         ";
        MagicCard m = new MagicCard(23, MagicType.REMOVE_BUFF, false, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString6() {
        String str = "99_ADULT_BULLYING:       VAL  8";
        MagicCard m = new MagicCard(99, MagicType.ATTACK_PLAYER, true, 8);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString7() {
        String str = "1_CRITIQUE               ";
        MagicCard m = new MagicCard(1, MagicType.REMOVE_BUFF, true, 0);
        assertEquals(str, m.toString());
    }

    @Test
    void testToString8() {
        String str = "1_FIREBALL:              VAL  4";
        MagicCard m = new MagicCard(1, MagicType.ATTACK_CARD, true, 4);
        assertEquals(str, m.toString());
    }
}