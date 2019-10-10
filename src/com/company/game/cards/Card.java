package com.company.game.cards;

abstract public class Card {

    private final int id;
    private final String name;

    public Card(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return null;
    }

}
