package com.company.game;

import com.company.game.cards.Card;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.players.Player;

import java.util.List;

public class GameEngine {

    private final Player[] players;
    private final RoundCounter roundCounter;

    public GameEngine(Player[] players, RoundCounter roundCounter) {
        this.players = players;
        this.roundCounter = roundCounter;
    }


    /**
     * @implNote attackPlayer or healPlayer
     * @param magicCard
     */
    public void engage(MagicCard magicCard) {

    }

    /**
     * @implNote if defense = 0, attack player
     * @param target
     * @param attacker
     * @return List[0] attacker card if it survives, List[1] defense cards if survives (filter card.calchp > 0)
     */
    public MonsterCard[] engage(MonsterCard target, MonsterCard attacker) {
        return new MonsterCard[]{target, attacker};
    }

    /**
     * @implNote if defense = 0, attack player
     * @param activeCard
     * @param targets
     * @return  List<Card> defense cards if survives (filter card.calchp > 0)
     */
    public Card[] engage(MagicCard activeCard, List<Card> targets) {
        return null;
    }


    /**
     * @return random int 1 - 6
     */
    private int throwDice() {
        return 0;
    }


    /**
     * @implNote attack player with card.attack / 2 rounded up
     * @param monsterCard
     */
    private void attackPlayer(MonsterCard monsterCard) {

    }
    /**
     * @implNote attack player with card.value
     * @param magicCard
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
