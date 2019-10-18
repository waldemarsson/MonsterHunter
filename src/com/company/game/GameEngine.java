package com.company.game;

import com.company.game.cards.BuffCard;
import com.company.game.cards.DebuffCard;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.enums.EffectType;
import com.company.game.enums.MagicType;
import com.company.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class GameEngine {

    private final Player[] players;
    private final RoundCounter roundCounter;

    public GameEngine(Player[] players, RoundCounter roundCounter) {
        this.players = players;
        this.roundCounter = roundCounter;
    }

    public boolean engage(MonsterCard monsterCard) {
        if (monsterCard == null) return false;

        attackPlayer(monsterCard);
        return true;
    }

    /**
     * @param magicCard
     * @implNote attackPlayer or healPlayer
     */
    public boolean engage(MagicCard magicCard) {
        List<MagicType> acceptedMagicTypes = List.of(MagicType.ATTACK_PLAYER, MagicType.HEAL_PLAYER);
        if (magicCard == null
                || !acceptedMagicTypes.contains(magicCard.getMagicType())
                || (acceptedMagicTypes.contains(magicCard.getMagicType()) && !magicCard.isTargeted())
        ) {
            return false;
        }

        switch (magicCard.getMagicType()) {
            case ATTACK_PLAYER:
                attackPlayer(magicCard);
                break;
            case HEAL_PLAYER:
                healPlayer(magicCard);
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * @param target
     * @param attacker
     * @return List[0] attacker card if it survives, List[1] defense cards if survives (filter card.calchp > 0)
     */
    public MonsterCard[] engage(MonsterCard target, MonsterCard attacker) {
        if (target == null || attacker == null || target == attacker) return new MonsterCard[]{null, null};

        return monsterOnMonster(target, attacker);
    }

    /**
     * @param magicCard
     * @param targets
     * @return List<MonsterCard> surviving cards
     * @implNote use private method
     */
    public List<MonsterCard> engage(MagicCard magicCard, List<MonsterCard> targets) {
        List<MagicType> notAcceptedMagicTypes = List.of(MagicType.ATTACK_PLAYER, MagicType.HEAL_PLAYER);
        if (magicCard == null || notAcceptedMagicTypes.contains(magicCard.getMagicType()) || targets.isEmpty() || (magicCard.isTargeted() && targets.size() > 1))
            return targets;


        List<String> rapport = new ArrayList<>();
        // STUN_BOMB_100 WAS USED ON OPPONENTS MONSTERS WITH 2 EFFECT
        // OPPONENTS BOARD AFTER MAGIC EFFECT
        rapport.add(magicCard.toString().replaceFirst(":.*", "")
                + " WAS USED ON "
                + (magicCard.getMagicType().isOnSelf() ? "YOUR" : "OPPONENT")
                + (magicCard.isTargeted() ? " MONSTER " : " MONSTERS ") + "WITH "
                + (magicCard.getMagicType().getAffectedField() == "BUFF" || magicCard.getMagicType().getAffectedField() == "DEBUFF" ? "" : magicCard.getValue()) + " "
                + "EFFECT ON "
                + magicCard.getMagicType().getAffectedField());

        rapport.add((magicCard.getMagicType().isOnSelf() ? "YOUR" : "OPPONENT") + (magicCard.isTargeted() ? " MONSTER " : " BOARD ") + "AFTER MAGIC EFFECT");
        List<MonsterCard> monsters = new ArrayList<>();
        for (MonsterCard monsterCard : targets) {

            MonsterCard card = magicOnMonster(magicCard, monsterCard);
            if (card != null && card.isAlive()) {
                monsters.add(card);
                rapport.add(card.toString() + " (ALIVE)");
            } else if (card != null && !card.isAlive()) {
                rapport.add(card.toString() + " (DEAD)");
            }

        }
        Game.getGameCLI().getOutputHandler().printRapport(rapport);
        return monsters;
    }

    /**
     * @return random int 1 - 6
     */
    private int throwDice() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }


    /**
     * @param monsterCard
     * @implNote attack player with card.attack / 2 rounded up
     */
    private void attackPlayer(MonsterCard monsterCard) {
        int damage = Math.max(0, (int) Math.ceil(monsterCard.getCalculatedAttack() / 2.0));
        monsterCard.addOneToFatigue();
        players[roundCounter.getOpponentIndex()].addDamage(damage);
        Game.getGameCLI().getOutputHandler().printRapport(List.of(
                monsterCard.toString(),
                "vs",
                players[roundCounter.getOpponentIndex()].toString(),
                "",
                players[roundCounter.getOpponentIndex()].getName().concat(" TOOK " + damage + " DAMAGE")
        ));
    }

    /**
     * @param magicCard
     * @implNote attack player with card.value
     */
    private void attackPlayer(MagicCard magicCard) {
        Player player = players[roundCounter.getOpponentIndex()];
        player.addDamage(magicCard.getValue());
        List<String> rapport = new ArrayList<>(List.of("ATTACK", player.getName() + " now has " + (player.getHp() - player.getDamage()) + " hp"));
        if (!player.isAlive()) rapport.add(player.getName() + " died");
        Game.getGameCLI().getOutputHandler().printRapport(rapport);
    }

    /**
     * @param magicCard
     * @implNote heal player with card.value
     */
    private void healPlayer(MagicCard magicCard) {
        Player player = players[roundCounter.getTurn()];
        Game.getGameCLI().getOutputHandler().printRapport(new ArrayList<>(List.of("HEAL", player.getName() + " now has " + (player.getHp() - player.getDamage()) + " hp")));
        player.heal(magicCard.getValue());
    }

    /**
     * @param attacker
     * @param target
     * @return Card[0] target, Card[1] attacker
     */
    private MonsterCard[] monsterOnMonster(MonsterCard target, MonsterCard attacker) {
        attacker.addOneToFatigue();
        int targetRoll = throwDice();
        int defendStat = target.getCalculatedDefense() + targetRoll;
        int attackerRoll = throwDice();
        int attackStat = attacker.getCalculatedAttack() + attackerRoll;
        List<String> rapport = new ArrayList<>(List.of("COMBAT",
                "\u001B[32m" + attacker.toString(),
                "Roll: " + attackerRoll + " Total attack: " + (attacker.getCalculatedAttack() + attackerRoll) + "\u001B[0m",
                "VS",
                "\u001B[31m" + target.toString(),
                "Roll: " + targetRoll + " Total defense: " + (target.getCalculatedDefense() + targetRoll) + "\u001B[0m",
                "",
                "RAPPORT"));

        if (attackStat - defendStat == 0) {
            rapport.add("Attacker did 0 damage");
        } else {

            boolean attackerWon = attackStat > defendStat;
            int damage = Math.abs(attackStat - defendStat);
            String str;

            if (attackerWon) {
                rapport.add("Attacker did " + damage + " damage");
                target.addDamage(damage);
                target = target.isAlive() ? target : null;
                str = target == null ? "Target died" : "Target survived with " + target.getCalculatedHealth() + " hp";
            } else {
                rapport.add("Attacker took " + damage + " damage");
                attacker.addDamage(damage);
                attacker = attacker.isAlive() ? attacker : null;
                str = attacker == null ? "Attacker died" : "Attacker survived with " + attacker.getCalculatedHealth() + " hp";
            }
            rapport.add(str);
        }
        Game.getGameCLI().getOutputHandler().printRapport(rapport);
        return new MonsterCard[]{target, attacker};
    }

    private MonsterCard magicOnMonster(MagicCard magicCard, MonsterCard monsterCard) {
        if (magicCard == null || monsterCard == null) return null;

        switch (magicCard.getMagicType()) {
            case STUN:
                monsterCard.addFatigue(magicCard.getValue());
                break;
            case ATTACK_CARD:
                monsterCard.addDamage(magicCard.getValue());
                break;
            case REMOVE_BUFF:
                monsterCard.setBuffCard(new BuffCard(0, 0, EffectType.NONE));
                break;
            case REMOVE_DEBUFF:
                monsterCard.setDebuffCard(new DebuffCard(0, 0, EffectType.NONE));
                break;
            case HEAL_CARD:
                monsterCard.heal(magicCard.getValue());
                break;
            default:
                return null;
        }
        return monsterCard;
    }


}
