package com.company.game.cards;

abstract public class Card {

    private final int id;
    private final String name;

    public Card(int id, String name) {
        this.id = Math.max(0, id);
        this.name = name != null ? name.replaceAll("(?i)[^a-z__]", "").toUpperCase() : "";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName().concat("_" + getId() + ": ");
    }

}
