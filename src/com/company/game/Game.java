package com.company.game;


import com.company.game.cli.GameCLI;
import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;

public class Game {

    private final RoundCounter roundCounter;
    private final Player[] players;
    private final Board board;
    private final DeckFactory deckFactory;
    private static GameCLI gameCLI = null;


    public Game() {
        roundCounter = new RoundCounter();
        deckFactory = new DeckFactory();
        Player player1 = new Player("Player1", deckFactory.buildDeck(30,10,10,5,5));
        Player player2 = new Player("Player2", deckFactory.buildDeck(30,10,10,5,5));
        players = new Player[]{player1, player2};
        board = new Board(roundCounter, players);
        player1.setBoard(board);
        player2.setBoard(board);
        gameCLI = new GameCLI(board, players, roundCounter);

        this.run();
    }


    private void run() {
        gameCLI.getOutputHandler().printBoard();
        while (players[0].isAlive() && players[1].isAlive()){
            gameCLI.getInputHandler().takeInput();
        }
        gameCLI.getOutputHandler().printWinner();
    }

    public static GameCLI getGameCLI() {
        return gameCLI;
    }
}
