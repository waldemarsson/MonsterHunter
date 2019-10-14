package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.players.Player;

public class GameCLI {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public GameCLI(Board board, Player[] players, RoundCounter roundCounter) {
        this.inputHandler = new InputHandler(board, players, roundCounter);
        this.outputHandler = new OutputHandler(board, players, roundCounter);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }
}
