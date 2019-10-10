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
        if(stamina <= 0) throw new IllegalArgumentException("Stamina must be bigger than 0");
        this.stamina = stamina;
        if(hp <= 0) throw new IllegalArgumentException("Hp must be bigger than 0");
        this.hp = hp;
        if(attack <= 0) throw new IllegalArgumentException("Attack must be bigger than 0");
        this.attack = attack;
        if(defense <= 0) throw new IllegalArgumentException("Defence must be bigger than 0");
        this.defense = defense;
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
