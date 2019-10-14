package com.company.game;

public class RoundCounter {

    private int currentRound;

    public RoundCounter() {
        currentRound = 0;
    }


    /**
     * @return % 2 of currentRound
     *
     */
    public int getTurn() {
        return currentRound % 2;
    }


    /**
     * increase currentRound
     */
    public void nextTurn() {
        currentRound++;
    }
}
