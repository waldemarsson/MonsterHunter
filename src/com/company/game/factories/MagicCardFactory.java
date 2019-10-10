package com.company.game.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MagicCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagicCardFactory {
    // STUN, HEAL_CARD, HEAL_PLAYER, ATTACK_CARD, ATTACK_PLAYER, REMOVE_BUFF, REMOVE_DEBUFF


    private List<String> magicCardNames;

    public MagicCardFactory() {
        magicCardNames = new ArrayList<>(Arrays.asList("Hunter", "Frog", "Donkey", "Knight", "Car", "Ninja", "Dennis", "Ant", "Dragon"));
    }

    public MagicCard buildCard(int id) {
        return null;
    }


    /**
     * @return 3 - 5
     */
    private int getRandomValue() {
        return 0;
    }

    private String getRandomMonsterName(){
        return null;
    }

    private BuffCard getRandomBonus() {
        return null;
    }
}
