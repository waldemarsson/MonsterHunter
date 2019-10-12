package com.company.tests.factories;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;
import com.company.game.factories.MagicCardFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicCardFactoryTest {

    MagicCardFactory magicCardFactory;
    int id = 0;

    @Test
    void buildCard() {
        magicCardFactory = new MagicCardFactory();
        for (int i = 0; i < 40; i++) {
            MagicCard c = magicCardFactory.buildCard(id);
            assertEquals(id, c.getId());
            assertNotNull(c.getMagicType());
            assertSame(MagicType.class, c.getMagicType().getClass());
            assertTrue(c.getValue() >= 3 || c.getValue() <= 5);
            id++;
        }
    }
}