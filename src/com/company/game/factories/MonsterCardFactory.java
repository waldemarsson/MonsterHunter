package com.company.game.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MonsterCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MonsterCardFactory {

    private List<String> monsterCardNames;

    public MonsterCardFactory() {
        monsterCardNames = new ArrayList<>(Arrays.asList("Hunter", "Frog", "Donkey", "Knight", "Corvette", "Ninja", "Helicopter", "Ant", "Dragon", "Warthog", "Tank", "Wizard", "Troll", "Gorilla", "Zorg", "General", "Stormtrooper", "Yoda", "Wookie"));
    }

    public MonsterCard buildCard(int id) {
        return null;
    }

    public MonsterCard buildCard(int id, BuffCard bonus) {
        return null;
    }

    private int getRandomValue(int min, int max) {
        return ThreadLocalRandom.current().nextInt(Math.max(min, 0), Math.max(max, 1) + 1);
    }

    private String getRandomMonsterName() {
        return monsterCardNames.get(getRandomValue(0, monsterCardNames.size() - 1));
    }

}
