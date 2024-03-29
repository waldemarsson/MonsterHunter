package com.company.tests.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import com.company.game.factories.MonsterCardFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonsterCardFactoryTest {

    MonsterCardFactory monsterCardFactory = new MonsterCardFactory();
    int id = 0;

    @BeforeEach
    void setUp() {
    }

    @Test
    void buildCard() {
        for (int i = 0; i < 20; i++) {
            MonsterCard mc = new MonsterCardFactory().buildCard(id);
            assertNotNull(mc);
            assertEquals(id, mc.getId());
            id++;
        }
    }

    @Test
    void buildCardWithBonus() {
        for (int i = 0; i < 20; i++) {
            MonsterCard mc = new MonsterCardFactory().buildCard(id, new BuffCard(0, 200, EffectType.DEFENSE));
            assertNotNull(mc.getBonus());
            assertEquals(id, mc.getId());
            id++;
        }
    }

    @Test
    void getRandomValue() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = monsterCardFactory.getClass().getDeclaredMethod("getRandomValue", int.class, int.class);
        method.setAccessible(true);

        for (int i = 0; i < 50; i++) {
            int test = (int) method.invoke(monsterCardFactory, 1, 4);
            assertTrue(test >= 1 && test <= 4);
        }
        for (int i = 0; i < 50; i++) {
            int test = (int) method.invoke(monsterCardFactory, -1, -2111);
            assertTrue(test >= 0 && test <= 4);
        }
    }

    @Test
    void getRandomMonsterName() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Field field = monsterCardFactory.getClass().getDeclaredField("monsterCardNames");
        field.setAccessible(true);
        List<String> listOfNames = (List<String>) field.get(monsterCardFactory);
        Method method = monsterCardFactory.getClass().getDeclaredMethod("getRandomMonsterName");
        method.setAccessible(true);

        for (int i = 0; i < 30; i++) {
            String test = (String) method.invoke(monsterCardFactory);
            assertTrue(listOfNames.contains(test));
        }


    }
}