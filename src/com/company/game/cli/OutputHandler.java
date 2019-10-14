package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.players.Player;

import java.util.List;

public class OutputHandler {

    private final Player[] players;
    private final Board board;
    private final RoundCounter roundCounter;


    public OutputHandler(Board board, Player[] players, RoundCounter roundCounter) {
        this.board = board;
        this.players = players;
        this.roundCounter = roundCounter;
    }

    //    #################################################
    //    Opponent: HP ?
    //    Cards on board:
    //    Card1
    //    Card2
    //    Card3
    //    ################################################
    //    You: HP ?
    //    Cards on board:
    //    Card1
    //    Card2
    //    Card3
    //    ################################################
    public void printBoard() {
    }

    //    Cards left in deck: 12
    //    Your hand:
    //    Card1
    //    Card2
    //    Card3
    //    osv..
    public void printHand() {

    }

    //    ####################################
    //
    //    INFO ABOUT WHAT HAPPEND
    //    EX: ARCHER_5 took 5 damage
    //
    //    #####################################
    public void printRapport(List<String> rapport) {

    }

    //    SHOW if monster
    //    NAME
    //    STAMINA
    //    HP
    //    ATTACK
    //    DEFENSE
    //    BONUS
    //    BUFF
    //    DEBUFF

    //    SHOW if magic or effect
    //    NAME
    //    ENUM
    //    VALUE
    public void printCard() {

    }


    //    HELP:
    //    USE: Use magic on player/card(s)
    //    ATTACK: Attack with monster from board
    //    PUT: Put a monster on the board or an effect on monster
    //    SHOW: Show more info about a card
    //    DONE: Done with your round
    //    FORFEIT: Admit loss
    public void printHelp() {

    }

    //    Player[]:
    private void printNextCommand() {

    }
}
