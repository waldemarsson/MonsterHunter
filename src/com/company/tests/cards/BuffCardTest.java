package com.company.tests.cards;

import com.company.game.cards.BuffCard;
import com.company.game.enums.EffectType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuffCardTest {

    @Test
    void testToString() {
        assertEquals("10_DEFENSE:              VAL +3", new BuffCard(10, 3, EffectType.DEFENSE).toString());
    }
}