package com.company.tests.factories;

import com.company.game.cards.EffectCard;
import com.company.game.cards.MagicCard;
import com.company.game.enums.EffectType;
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
            id++;
            MagicCard c2 = magicCardFactory.buildCard(id);
            id++;

            assertNotNull(c);
            assertNotNull(c2);
            assertNotEquals(c.getId(), c2.getId());
        }
    }
}