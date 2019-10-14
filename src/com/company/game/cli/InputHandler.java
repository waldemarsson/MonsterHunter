package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.players.Player;

import java.util.Scanner;

public class InputHandler {

    private final Player[] players;
    private final Board board;
    private final RoundCounter roundCounter;
    private final Scanner input;


    public InputHandler(Board board, Player[] players, RoundCounter roundCounter) {
        this.board = board;
        this.players = players;
        this.roundCounter = roundCounter;
        this.input = new Scanner(System.in);
    }


    /**
     * @implNote USE / ATTACK / PUT / SHOW / DONE / FORFEIT / HELP
     *
     */
    private void takeInput() {
        //switch input make thing

        // if board returns false, print "INVALID COMMAND "USEE CARD"
    }
}
