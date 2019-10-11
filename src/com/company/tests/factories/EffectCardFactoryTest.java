package com.company.tests.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.DebuffCard;
import com.company.game.cards.EffectCard;
import com.company.game.enums.EffectType;
import com.company.game.factories.EffectCardFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class EffectCardFactoryTest {

    @Test
    void constructorTest(){
        try {
            new EffectCardFactory();
        } catch (Exception e){
            fail();
        }
    }

    @Test
    void constructorProducedArrayTest(){
        try{
            Field field = EffectCardFactory.class.getDeclaredField("effects");
            field.setAccessible(true);
            EffectType[] effects = (EffectType[]) field.get(new EffectCardFactory());
            assertNotEquals(0, effects.length, "EffectCardFactory effects is empty");
        } catch(Exception e) {
            fail("Failed to create effects");
        }
    }

    @Test
    void buildBuffCard(){
        assertTrue(new EffectCardFactory().buildEffectCard(1, true) instanceof BuffCard);
    }

    @Test
    void buildDebuffCard(){
        assertTrue(new EffectCardFactory().buildEffectCard(1, false) instanceof DebuffCard);
    }

    @Test
    void build1000RandomEffectCards(){
        EffectCardFactory factory = new EffectCardFactory();
        Set<String> types = new HashSet<>();
        Set<String> identifiers = new HashSet<>();
        Set<String> uniques = new HashSet<>();
        for(int i = 0; i < 10000; i++){
            EffectCard card = factory.buildEffectCard(i+1, ThreadLocalRandom.current().nextBoolean());
            types.add(card.toString().replaceAll("[^A-Z]", ""));
            identifiers.add(card.toString().replaceAll("_[0-9]+", ""));
            uniques.add(card.toString());
        }
        assertEquals(3, types.size());
        assertEquals(12, identifiers.size());
        assertEquals(10000, uniques.size());
    }
}