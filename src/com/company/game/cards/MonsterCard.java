package com.company.game.cards;

import com.company.game.collections.Deck;
import com.company.game.enums.EffectType;

public class MonsterCard extends Card {

    private int stamina;
    private int fatigue;
    private final int hp;
    private int damage;
    private int attack;
    private int defense;
    private BuffCard buffCard;
    private DebuffCard debuffCard;
    private final BuffCard bonus;

    public MonsterCard(int id, String name, int stamina, int hp, int attack, int defense, BuffCard bonus) {
        super(id, bonus != null && bonus.getEffectType() !=  EffectType.NONE ? "EPIC_" + name : name);
        this.stamina = (stamina <= 0 || stamina > 2) ? 1 : stamina;
        this.hp = (hp < 3 || hp > 8) ? 5 : hp;
        this.attack = (attack < 2 || attack > 7) ? 4 : attack;
        this.defense = (defense < 2 || defense > 7) ? 4 : defense;
        this.bonus = bonus;
        this.fatigue = Integer.MAX_VALUE;
        this.damage = 0;
        this.buffCard = new BuffCard(0, 0, EffectType.NONE);
        this.debuffCard = new DebuffCard(0, 0, EffectType.NONE);
        if(bonus.getEffectType() != EffectType.NONE){
            switch (bonus.getEffectType()) {
                case ATTACK:
                    this.attack = 7;
                    break;
                case DEFENSE:
                    this.defense = 7;
                    break;
                case STAMINA:
                    this.stamina = 2;
                    break;
                default:
                    break;
            }
        }
    }

    public int getCalculatedStamina() {
        return fatigue == Integer.MAX_VALUE ? 0 : stamina - fatigue + bonus.getStaminaEffect() + buffCard.getStaminaEffect() + debuffCard.getStaminaEffect();
    }

    public int getHp() {
        return hp;
    }

    public int getCalculatedHealth() {
        return hp - damage;
    }

    public boolean isAlive() {
        return getCalculatedHealth() > 0;
    }

    public int getDamage() {
        return damage;
    }

    public int getCalculatedAttack() {
        return attack + bonus.getAttackEffect() + buffCard.getAttackEffect() + debuffCard.getAttackEffect();
    }

    public int getCalculatedDefense() {
        return defense + bonus.getDefenseEffect() + buffCard.getDefenseEffect() + debuffCard.getDefenseEffect();
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
        damage += Math.max(0, damageTaken);
    }

    public void addFatigue(int value) {
        fatigue += Math.max(0, value);
    }

    public void heal(int value) {
        value = Math.max(0, value);
        damage = Math.max(0, damage - value);
    }

    public void addOneToFatigue() {
        fatigue++;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void resetFatigue() {
        fatigue = 0;
    }

    public void setDebuffCard(DebuffCard debuffCard) {
        if (debuffCard != null) {
            this.debuffCard = debuffCard;
        }
    }

    public void setBuffCard(BuffCard buffCard) {
        if (buffCard != null) {
            this.buffCard = buffCard;
        }
    }

    public int getStamina() {
        return stamina;
    }



    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(String.format("%1$-25s", super.toString()));
        String stringHP = String.format("%1$-15s", "HP " + getCalculatedHealth() + "/" + hp);
        str.append(stringHP);
        String stringStamina = " STA " + getCalculatedStamina();
        stringStamina = stringStamina.concat(String.format("/%d", (stamina+bonus.getStaminaEffect()+debuffCard.getStaminaEffect()+buffCard.getStaminaEffect())));
        if (buffCard.getStaminaEffect() > 0) stringStamina = stringStamina.concat("(+" + buffCard.getStaminaEffect() + ")");
        if (debuffCard.getStaminaEffect() < 0) stringStamina = stringStamina.concat("(" + debuffCard.getStaminaEffect() + ")");
        str.append(String.format("%1$-15s", stringStamina));
        String stringAttack = " ATT " + getCalculatedAttack();
        if (buffCard.getAttackEffect() > 0) stringAttack = stringAttack.concat("(+" + buffCard.getAttackEffect() + ")");
        if (debuffCard.getAttackEffect() < 0) stringAttack = stringAttack.concat("(" + debuffCard.getAttackEffect() + ")");
        str.append(String.format("%1$-15s", stringAttack));
        String stringDefense = " DEF " + getCalculatedDefense();
        if (buffCard.getDefenseEffect() > 0) stringDefense = stringDefense.concat("(+" + buffCard.getDefenseEffect() + ")");
        if (debuffCard.getDefenseEffect() < 0) stringDefense = stringDefense.concat("(" + debuffCard.getDefenseEffect() + ")");
        str.append(String.format("%1$-15s", stringDefense));

        return str.toString();
    }
}
