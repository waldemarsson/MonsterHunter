package com.company.game;


import com.company.game.cli.GameCLI;
import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;

public class Game {

    private final RoundCounter roundCounter;
    private final Player[] players;
    private final Board board;
    private final DeckFactory deckFactory;
    private final GameCLI gameCLI;


    public Game() {
        roundCounter = new RoundCounter();
        deckFactory = new DeckFactory();
        Player player1 = new Player("Player1", deckFactory.buildDeck(0,0,0,0,0));
        Player player2 = new Player("Player2", deckFactory.buildDeck(0,0,0,0,0));
        players = new Player[]{player1, player2};
        board = new Board(roundCounter, players);
        player1.setBoard(board);
        player2.setBoard(board);
        gameCLI = new GameCLI(board, players, roundCounter);
    }


    private void run() {

    }
}
