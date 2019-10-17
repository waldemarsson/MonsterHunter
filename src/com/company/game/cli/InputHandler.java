package com.company.game.cli;

import com.company.game.Board;
import com.company.game.Game;
import com.company.game.RoundCounter;
import com.company.game.players.Player;

import java.util.Scanner;

public class InputHandler {

    private final Player[] players;
    private final Board board;
    private final RoundCounter roundCounter;
    private final Scanner inputScanner;


    public InputHandler(Board board, Player[] players, RoundCounter roundCounter) {
        this.board = board;
        this.players = players;
        this.roundCounter = roundCounter;
        this.inputScanner = new Scanner(System.in);
    }


    /**
     * @implNote USE / ATTACK / PUT / SHOW / DONE / FORFEIT / HELP / BOARD
     *
     */
    public void takeInput() {
        //switch input make thing
        // if board returns false, print "INVALID COMMAND "USEE CARD"

        String[] input = inputScanner.nextLine().trim().toUpperCase().split(" ");

        switch (input[0]){
            case "":
            case "BOARD":
                Game.getGameCLI().getOutputHandler().printBoard();
                break;
            case "SHOW":
                try{
                    Game.getGameCLI().getOutputHandler().printCard(Integer.parseInt(input[1]));
                }catch(Exception e){
                    Game.getGameCLI().getOutputHandler().printError(String.join(" ", input));
                }
                break;
            case "ATTACK":
                if(!attack(input)) Game.getGameCLI().getOutputHandler().printError(String.join(" ", input));

                break;
            case "USE":
                if(!use(input)) Game.getGameCLI().getOutputHandler().printError(String.join(" ", input));

                break;
            case "PUT":
                if(!put(input)) Game.getGameCLI().getOutputHandler().printError(String.join(" ", input));
                else Game.getGameCLI().getOutputHandler().printBoard();
                break;
            case "DONE":
                board.nextRound();
                Game.getGameCLI().getOutputHandler().printBoard();
                break;
            case "FORFEIT":
                players[roundCounter.getTurn()].addDamage(players[roundCounter.getTurn()].getHp() - players[roundCounter.getTurn()].getDamage());
                break;
            case "HELP":
                Game.getGameCLI().getOutputHandler().printHelp();
                break;
            default:
                Game.getGameCLI().getOutputHandler().printError(String.join(" ", input));
                break;
        }
    }

    private boolean attack(String[] cmd){
        boolean success = false;
        try{
            if(cmd.length == 4 && cmd[2].equals("WITH") && cmd[1].matches("^\\d+$") && cmd[3].matches("^\\d+$")){
                success = board.attackMonsterWithMonster(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[3]));
            } else if(cmd.length == 3 && cmd[1].equals("WITH") && cmd[2].matches("^\\d+$")) {
                success = board.attackPlayerWithMonster(Integer.parseInt(cmd[2]));
            }
        } catch (Exception e) {}
        return success;
    }

    private boolean use(String[] cmd){
        boolean success = false;
        try{
            if(cmd.length == 4 && cmd[2].equals("ON") && cmd[1].matches("^\\d+$") && cmd[3].matches("^\\d+$")){
                success = players[roundCounter.getTurn()].useMagicOnBoardFromHand(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[3]));
            } else if(cmd.length == 2 && cmd[1].matches("^\\d+$")) {
                success = players[roundCounter.getTurn()].useMagicOnBoardFromHand(Integer.parseInt(cmd[1]));
            }
        } catch (Exception e) {}
        return success;
    }

    private boolean put(String[] cmd){
        boolean success = false;
        try{
            if(cmd.length == 4 && cmd[2].equals("ON") && cmd[1].matches("^\\d+$") && cmd[3].matches("^\\d+$")){
                success = players[roundCounter.getTurn()].placeEffectOnBoardFromHand(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[3]));
            } else if(cmd.length == 2 && cmd[1].matches("^\\d+$")) {
                success = players[roundCounter.getTurn()].placeMonsterOnBoardFromHand(Integer.parseInt(cmd[1]));
            }
        } catch (Exception e) { }
        return success;
    }
}
