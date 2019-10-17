package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.cards.*;
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



    public void printCard(Card card) {
        switch (card.getClass().getSimpleName()){
            case "MonsterCard":
                showMonsterCard((MonsterCard) card);
                break;
            case "BuffCard":
                showBuffCard((BuffCard) card);
                break;
            case "DebuffCard":
                showDeBuffCard((DebuffCard) card);
                break;
            case "MagicCard":
                showMagicCard((MagicCard) card);
                break;
        }
    }

    private void showMonsterCard(MonsterCard card){
        DebuffCard debuffCard = card.getDebuffCard();
        BuffCard bonus = card.getBonus();
        BuffCard buffCard = card.getBuffCard();
        printer(List.of(
                card.getName().concat("_").concat(Integer.toString(card.getId())),
                "HEALTH: " +
                        String.format("%d/%d", card.getCalculatedHealth(), card.getHp()),
                "STAMINA: " +
                        String.format("%d/%d", card.getCalculatedStamina(),
                                (card.getStamina()+buffCard.getStaminaEffect()+bonus.getStaminaEffect()+debuffCard.getStaminaEffect())),
                "ATTACK: " +
                        Integer.toString(card.getCalculatedAttack())
                                .concat(buffCard.getAttackEffect() > 0 ?  String.format("(+%d)", buffCard.getAttackEffect()) : "")
                                .concat(debuffCard.getAttackEffect() < 0 ? String.format("(%d)", debuffCard.getAttackEffect()) : ""),
                "DEFENSE: " +
                        Integer.toString(card.getCalculatedDefense())
                                .concat(buffCard.getDefenseEffect() > 0 ? String.format("(+%d)", buffCard.getDefenseEffect()) : "")
                                .concat(debuffCard.getDefenseEffect() < 0 ? String.format("(%d)", debuffCard.getDefenseEffect()) : ""),
                "",
                "BONUS: " +
                        bonus.toString().replaceAll("_\\d:", ""),
                "BUFFCARD: " +
                        buffCard.toString().replaceAll("_\\d:", ""),
                "DEBUFFCARD: " +
                        debuffCard.toString().replaceAll("_\\d:", "")
        ));
    }

    private void showBuffCard(BuffCard card){
        printer(List.of(
                "This is a buff card that improves the stats of one of your monsters. ",
                "Buff cards can be placed on your monsters once they have been placed on the board. ",
                card.toString()
        ));
    }

    private void showDeBuffCard(DebuffCard card){
        printer(List.of(
                "This is a debuff card that decreases the stats of one of your opponents monsters. ",
                "Buff cards can be placed on your opponents monsters once they have been placed on the board. ",
                card.toString()
        ));
    }

    private void showMagicCard(MagicCard card){

    }

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

    private void printActivePlayerCommandLine() {
        System.out.print(players[roundCounter.getTurn()].getName().concat(": "));
    }

    // Private printer handles all output
    private void printer(List<String> output){
        clearConsole();

        System.out.println(outerDivider());

        output.forEach(System.out::println);

        System.out.println(outerDivider());

        printActivePlayerCommandLine();
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
