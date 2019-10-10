package com.company.game.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MonsterCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterCardFactory {

    private List<String> monsterCardNames;

    public MonsterCardFactory() {
        monsterCardNames = new ArrayList<>(Arrays.asList("Hunter", "Frog", "Donkey", "Knight", "Car", "Ninja", "Dennis", "Ant", "Dragon"));
    }

    public MonsterCard buildCard(int id) {
        return null;
    }

    public MonsterCard buildCard(int id, BuffCard bonus) {
        return null;
    }

    private int getRandomValue(int min, int max) {
        return 0;
    }

    private String getRandomMonsterName() {
        return null;
    }

}
