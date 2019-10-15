package com.company.game.players;

import com.company.game.Board;
import com.company.game.Game;
import com.company.game.cards.Card;
import com.company.game.cards.EffectCard;
import com.company.game.cards.MonsterCard;
import com.company.game.collections.Deck;
import com.company.game.collections.Hand;

import java.util.List;

public class Player {

    private final String name;
    private final int hp;
    private int damage;
    private final Deck deck;
    private final Hand hand;
    private Board board;

    public Player(String name, Deck deck) {
        this.name = name == null || name.trim().equals("") ? "UNKNOWN_PLAYER" : name.trim().toUpperCase();
        this.deck = deck != null ? deck : new Deck(List.of());
        this.hp = 20;
        this.damage = 0;
        this.hand = new Hand();
        for(int i = 0; i < 5; i++)
            drawFromDeckToHand();
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
        this.damage += Math.max(damageTaken, 0);
    }

    public Hand getHand() {
        return hand;
    }

    public void setBoard(Board board) {
        if (board != null) {
            this.board = board;
        }
    }

    public List<String> getHandAsString() {
        return hand.getCardsOnHandAsString();
    }

    /**
     * @return boolean hp - damage > 0
     */
    public boolean isAlive() {
        return false;
    }

    public void heal(int value){
        value = Math.max(value, 0);
        this.damage = Math.max(0, this.damage - value);
    }

    public boolean drawFromDeckToHand(){
        boolean hasCards = deck.hasCards();
        if(hasCards)
            hand.putCard(deck.drawCard());
        return hasCards;
    }

    public boolean placeCardOnBoardFromHand(int id){
        Card card = hand.hasCard(id) ? hand.playCard(id) : null;
        boolean wasPlaced = false;

        if(card instanceof MonsterCard){
            wasPlaced = board.placeMonsterOnBoard((MonsterCard) card);

            if(!wasPlaced) {
                hand.putCard(card);
            }
        }
        return wasPlaced;
    }

    public boolean placeCardOnBoardFromHand(int effectId, int monsterId){
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
