package com.company.game.cards;

public class MonsterCard extends Card {

    private final int stamina;
    private int fatigue;
    private final int hp;
    private int damage;
    private final int attack;
    private final int defense;
    private BuffCard buffCard;
    private DebuffCard debuffCard;
    private final BuffCard bonus;

    public MonsterCard(int id, String name, int stamina, int hp, int attack, int defense, BuffCard bonus) {
        super(id, name);
        this.stamina = (stamina <= 0 || stamina > 2) ? 1 : stamina;
        this.hp = (hp < 3 || hp > 8) ? 5 : hp;
        this.attack = (attack < 2 || attack > 7) ? 4 : attack;
        this.defense = (defense < 2 || defense > 7) ? 4 : defense;;
        this.bonus = bonus;

        this.fatigue = 0;
        this.damage = 0;
        this.buffCard = null;
        this.debuffCard = null;
    }

    public int getStamina() {
        return stamina - fatigue;
    }

    public int getHp() {
        return hp - damage;
    }

    public int getDamage() {
        return damage;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public DebuffCard getDebuffCard() {
        return debuffCard;
    }

    public BuffCard getBuffCard() {
        return buffCard;
    }

    public BuffCard getBonus() {
        return bonus;
    }

    public void addDamage(int damageTaken) {
        damage += damageTaken;
    }

    public void addOneToFatigue() {

    }

    public void setDebuffCard(DebuffCard debuffCard) {
        this.debuffCard = debuffCard;
    }

    public void setBuffCard(BuffCard buffCard) {
        this.buffCard = buffCard;
    }

    @Override
    public String toString() {
        return null;
    }
}
