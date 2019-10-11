package com.company.game.cards;

import com.company.game.enums.EffectType;

abstract public class EffectCard extends Card {

    private final int value;
    private final EffectType effectType;

    public EffectCard(int id, String name, int value, EffectType effectType) {
        super(id, name);
        this.value = value;
        this.effectType = effectType;
    }

    public int getStaminaEffect() {

        return 0;
    }


    public int getAttackEffect() {

        return 0;
    }

    public int getDefenseEffect() {

        return 0;
    }

    @Override
    public String toString() {
        return null;
    }

}












