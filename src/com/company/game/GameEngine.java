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
        if (magicCard == null || !acceptedMagicTypes.contains(magicCard.getMagicType())) return false;

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
        if (magicCard == null || notAcceptedMagicTypes.contains(magicCard.getMagicType()) || targets.isEmpty() || magicCard.isTargeted())
            return targets;

        List<MonsterCard> monsters = new ArrayList<>();
        for (MonsterCard monsterCard : targets) {
            MonsterCard card = magicOnMonster(magicCard, monsterCard);
            if (card != null && card.isAlive()) {
                monsters.add(card);
            }
        }
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
        players[roundCounter.getOpponentIndex()].addDamage(damage);
    }

    /**
     * @param magicCard
     * @implNote attack player with card.value
     */
    private void attackPlayer(MagicCard magicCard) {
        Player player = players[roundCounter.getOpponentIndex()];
        player.addDamage(magicCard.getValue());
        List<String> rapport = new ArrayList<>(List.of("ATTACK", player.getName() + " now has " + player.getHp() + " hp"));
        if (!player.isAlive()) rapport.add(player.getName() + " died");
        Game.getGameCLI().getOutputHandler().printRapport(rapport);
    }

    /**
     * @param magicCard
     * @implNote heal player with card.value
     */
    private void healPlayer(MagicCard magicCard) {
        Player player = players[roundCounter.getTurn()];
        Game.getGameCLI().getOutputHandler().printRapport(new ArrayList<>(List.of("HEAL", player + " now has " + player.getHp() + " hp")));
        player.heal(magicCard.getValue());
    }

    /**
     * @param attacker
     * @param target
     * @return Card[0] target, Card[1] attacker
     */
    private MonsterCard[] monsterOnMonster(MonsterCard target, MonsterCard attacker) {
        int targetRoll = throwDice();
        int defendStat = target.getCalculatedDefense() + targetRoll;
        int attackerRoll = throwDice();
        int attackStat = attacker.getCalculatedAttack() + attackerRoll;
        List<String> rapport = new ArrayList<>(List.of("COMBAT",
                attacker.toString(),
                "Roll: ",
                String.valueOf(attackerRoll),
                "VS",
                target.toString(),
                "Roll: ",
                String.valueOf(targetRoll),
                "",
                "RAPPORT"));

        if (attackStat - defendStat == 0) {
            rapport.add("Attacker did 0 damage");
            return new MonsterCard[]{target, attacker};
        }

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
                monsterCard.setDebuffCard(new DebuffCard(0, 0, EffectType.NONE));
                break;
            case REMOVE_DEBUFF:
                monsterCard.setBuffCard(new BuffCard(0 ,0, EffectType.NONE));
            case HEAL_CARD:
                monsterCard.heal(magicCard.getValue());
                break;
            default:
                return null;
        }
        return monsterCard;
    }


}
