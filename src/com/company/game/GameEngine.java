package com.company.game;

import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.players.Player;

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
        return true;
    }

    /**
     * @param magicCard
     * @implNote attackPlayer or healPlayer
     */
    public boolean engage(MagicCard magicCard) {
        return true;
    }

    /**
     * @param target
     * @param attacker
     * @return List[0] attacker card if it survives, List[1] defense cards if survives (filter card.calchp > 0)
     * @implNote if defense = 0, attack player
     */
    public MonsterCard[] engage(MonsterCard target, MonsterCard attacker) {
        return new MonsterCard[]{target, attacker};
    }

    /**
     * @param activeCard
     * @param targets
     * @return List<MonsterCard> defense cards if survives (filter card.calchp > 0)
     * @implNote if defense = 0, attack player
     */
    public List<MonsterCard> engage(MagicCard activeCard, List<MonsterCard> targets) {
        return targets;
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

    }

    /**
     * @param magicCard
     * @implNote attack player with card.value
     */
    private void attackPlayer(MagicCard magicCard) {

    }

    /**
     * @param attacker
     * @param defender
     * @return Card[0] attacker, Card[1] defender
     */
    private MonsterCard[] monsterOnMonster(MonsterCard attacker, MonsterCard defender) {
        return null;
    }

    private MonsterCard magicOnMonster(MagicCard magicCard, MonsterCard monsterCard) {
        return null;
    }


}
