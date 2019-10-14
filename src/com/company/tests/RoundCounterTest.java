package com.company.tests;

import com.company.game.RoundCounter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundCounterTest {

    RoundCounter roundCounter;

    @BeforeEach
    void setUp() {
        roundCounter = new RoundCounter();
    }


    @Test
    void constructor() {
        try{
            new RoundCounter();
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getTurn() {
        for(int i = 0; i < 1000; i++) {
            assertTrue(roundCounter.getTurn() == 0 || roundCounter.getTurn() == 1);
            roundCounter.nextTurn();
        }
    }

    @Test
    void nextTurn() {
        for(int i = 0; i < 1000; i++) {
            int turn = roundCounter.getTurn();
            roundCounter.nextTurn();
            assertNotEquals(turn, roundCounter.getTurn());
        }
    }
}