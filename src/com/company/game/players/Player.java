package com.company.game.players;

import com.company.game.Board;
import com.company.game.collections.Deck;
import com.company.game.collections.Hand;

public class Player {

    private final String name;
    private final int hp;
    private int damage;
    private final Deck deck;
    private final Hand hand;
    private final Board board;

    public Player(String name, Deck deck, Board board) {
        this.name = name;
        this.deck = deck;
        this.board = board;
        this.hp = 20;
        this.damage = 0;
        this.hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void addDamage(int damageTaken) {

    }

    /**
     * @return boolean hp - damage > 0
     */
    public boolean isAlive() {
        return false;
    }
}
