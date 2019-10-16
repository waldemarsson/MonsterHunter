package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.cards.Card;
import com.company.game.players.Player;

import javax.sound.midi.Soundbank;
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

    public void printError(String invalidCommand){
        printer(List.of(invalidCommand, "is not a valid command or move", "try typing HELP for more information"));
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
        printer(rapport);
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
    public void printCard(Card card) {

    }


    //    HELP:
    //    USE: Use magic on player/card(s)
    //    ATTACK: Attack with monster from board
    //    PUT: Put a monster on the board or an effect on monster
    //    SHOW: Show more info about a card
    //    DONE: Done with your round
    //    FORFEIT: Admit loss
    public void printHelp() {

        printer(List.of(
                "HELP SECTION FOR MONSTER HUNTER",
                innerDivider(),
                "COMMANDS AND HOW TO USE THEM:",
                "ALL PLAYABLE CARDS ARE LISTED AS 'NAME_ID'",
                "YOU REFER TO ALL CARDS BY THEIR ID",
                "YOU CAN ONLY USE COMMANDS ON CARDS THAT ARE VISIBLE ON YOUR HAND OUR THE BOARD",
                innerDivider(),
                "'USE' : COMMAND FOR USING MAGIC CARDS",
                "USAGE EXAMPLE >> USE 15 ON 84 (WILL USE THE MAGIC SPELL OF CARD 15 ON MONSTER WITH ID 84)",
                "USAGE EXAMPLE >> USE 44 (WILL USE MAGIC ON MULTIPLE MONSTERS OR A PLAYER)",
                "",
                "'PUT' : COMMAND FOR PLACING A MONSTER ON THE BOARD OR AN EFFECT ON A MONSTER THAT HAS BEEN PLACED ON THE BOARD",
                "USAGE EXAMPLE >> PUT 15 (PLACES MONSTER)",
                "USAGE EXAMPLE >> PUT 15 (PLACES MONSTER)",
                "",
                "'ATTACK' : COMMAND FOR ATTACKING OPPONENTS MONSTER WITH YOUR MONSTER, BOTH MONSTERS MUST BE VISIBLE ON THE BOARD",
                "ALSO USED FOR ATTACKING OPPONENT IF HE/SHE DOES NOT HAVE ANY MONSTERS TO DEFEND THEM",
                "USAGE EXAMPLE >> ATTACK 5 WITH 67 (WILL ATTACK MONSTER WITH ID 5 WITH MONSTER WITH ID 67)",
                "USAGE EXAMPLE >> ATTACK OPPONENT WITH 9",
                "",
                "'SHOW' : COMMAND FOR PRINTING MORE DETAILED INFORMATION ABOUT A CARD THAT IS ON THE BOARD OR IN YOUR HAND",
                "USAGE EXAMPLE >> SHOW 34 (PRINTS DETAILED INFORMATION ABOUT CARD 34)",
                "",
                "'DONE' : COMMAND FOR FINISHING YOUR TURN",
                "USAGE EXAMPLE >> DONE (IT IS NOW YOUR OPPONENTS TURN)",
                "",
                "'FORFEIT' : COMMAND FOR FORFEITING THE GAME",
                "USAGE EXAMPLE >> FORFEIT (OPPONENT IS NOW THE WINNER)"
        ));
    }

    //    Player[]:
    private void printNextCommand() {
        System.out.println(players[roundCounter.getTurn()].getName().concat(": "));
    }

    // Private printer handles all output
    private void printer(List<String> output){
        clearConsole();
        System.out.println(outerDivider());

        output.forEach(System.out::println);

        System.out.println(outerDivider());
        printNextCommand();
    }

    private void clearConsole(){
        String s = "";
        for(int i = 0; i < 100; i++)
            s = s.concat("\n");
        System.out.print(s);
    }

    private String outerDivider(){
        String s = "\n";
        for (int i = 0; i < 120; i++)
            s = s.concat("¤");
        return s.concat("\n");
    }

    private String innerDivider(){
        String s = "\n";
        for (int i = 0; i < 120; i++)
            s = s.concat("-");
        return s.concat("\n");
    }

}
