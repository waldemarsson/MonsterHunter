package com.company.tests.factories;

import com.company.game.cards.MonsterCard;
import com.company.game.factories.MonsterCardFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterCardFactoryTest {

    MonsterCardFactory monsterCardFactory = new MonsterCardFactory();
    int id = 0;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void addId() {
        id++;
    }

    @Test
    void buildCard() {
        MonsterCard m = new MonsterCardFactory().buildCard(id);
        assertNotNull(m);
    }

    @Test
    void testBuildCard() {
    }

    @Test
    void getRandomValue() {

    }

    @Test
    void getRandomMonsterName() {

    }
}