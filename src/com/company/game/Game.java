package com.company.game;


import com.company.game.factories.DeckFactory;
import com.company.game.players.Player;

public class Game {

    private final RoundCounter roundCounter;
    private final Player[] players;
    private final Board board;
    private final DeckFactory deckFactory;

    public Game() {
        roundCounter = new RoundCounter();
        board = new Board(roundCounter);
        deckFactory = new DeckFactory();
        Player player1 = new Player("Player1", deckFactory.buildDeck(0,0,0,0,0), board);
        Player player2 = new Player("Player2", deckFactory.buildDeck(0,0,0,0,0), board);
        players = new Player[]{player1, player2};

    }


    private void run() {

    }
}
