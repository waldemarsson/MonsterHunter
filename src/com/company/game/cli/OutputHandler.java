package com.company.game.cli;

import com.company.game.Board;
import com.company.game.RoundCounter;
import com.company.game.cards.*;
import com.company.game.players.Player;

import java.util.ArrayList;
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
        printer(List.of(invalidCommand, "IS NOT A VALID COMMAND OR MOVE", "TRY TYPING \"HELP\" FOR MORE INFORMATION"));
    }

    public void printBoard() {
        List<String> list = new ArrayList<>(getBoardForPrinting());
        list.addAll(getHandForPrinting());
        printer(list);
    }

    private List<String> getBoardForPrinting(){
        List<String> outputBoard = new ArrayList<>(List.of(
                "OPPONENT: ".concat(players[roundCounter.getOpponentIndex()].toString())
        ));
        outputBoard.addAll(board.getMonsterPile(roundCounter.getOpponentIndex()));
        outputBoard.add(innerDivider());
        outputBoard.add("YOU: ".concat(players[roundCounter.getTurn()].toString()));
        outputBoard.addAll(board.getMonsterPile(roundCounter.getTurn()));
        outputBoard.add(innerDivider());
        return outputBoard;
    }

    private List<String> getHandForPrinting() {
        List<String> hand = new ArrayList<>(List.of(
                "CARDS LEFT IN DECK: ".concat(Integer.toString(players[roundCounter.getTurn()].getCardsLeftInDeck())),
                players[roundCounter.getTurn()].getName().concat(" HAND: ")
        ));

        hand.addAll(players[roundCounter.getTurn()].getHand().getCardsOnHandAsString());

        return hand;
    }

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
        String s = card.toString().replaceAll(":.*", "");
        switch (card.getMagicType()){
            case ATTACK_CARD:
                s = s.concat(" attacks ")
                        .concat(card.isTargeted() ? "one monster " : "multiple monsters ")
                        .concat(" and deals " + card.getValue() + " damage. ");
                break;
            case HEAL_CARD:
                s = s.concat(" heals ")
                        .concat(card.isTargeted() ? "one monster " : "multiple monsters ")
                        .concat(" and removes " + card.getValue() + " damage. ");
                break;
            case STUN:
                s = s.concat(" stuns ")
                        .concat(card.isTargeted() ? "one monster " : "multiple monsters ")
                        .concat(" by removing " + card.getValue() + " stamina for one turn. ");
                break;
            case HEAL_PLAYER:
                s = s.concat(" heals you by removing " + card.getValue() + " damage.");
                break;
            case ATTACK_PLAYER:
                s = s.concat(" attacks your opponent by dealing " + card.getValue() + " damage. ");
                break;
            case REMOVE_BUFF:
                s = s.concat(" removes buffs on opponents monsters for ")
                        .concat(card.isTargeted() ? "one monster" : "multiple monsters");
                break;
            case REMOVE_DEBUFF:
                s = s.concat(" removes debuffs on your monsters for ")
                        .concat(card.isTargeted() ? "one monster" : "multiple monsters");
                break;
        }
        printer(List.of(
                s
        ));
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
                "'BOARD' : COMMAND FOR PRINTING THE CURRENT BOARD AND HAND",
                "USAGE EXAMPLE >> BOARD (PRINTS BOARD AND HAND)",
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
