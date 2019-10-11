package com.company.game.cards;

import com.company.game.enums.EffectType;

abstract public class EffectCard extends Card {

    private final int value;
    private final EffectType effectType;

    public EffectCard(int id, int value, EffectType effectType) {
        super(effectType == EffectType.NONE ? 0 : id, "");
        this.value = Math.max(0, value);
        this.effectType = effectType;
    }

    public int getStaminaEffect() {
        return effectType == EffectType.STAMINA ? value : 0;
    }

    public int getAttackEffect() {
        return effectType == EffectType.ATTACK ? value : 0;
    }

    public int getDefenseEffect() {
        return effectType == EffectType.DEFENSE ? value : 0;
    }

    @Override
    public String toString() {
        return null;
    }

    public int getValue() {
        return value;
    }
}












