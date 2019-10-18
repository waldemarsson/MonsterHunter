package com.company.game.cards;

import com.company.game.enums.EffectType;

public class DebuffCard extends EffectCard {

    public DebuffCard(int id, int value, EffectType effectType) {
        super(id, value, effectType);
    }


    /**
     * @return inverted value
     */
    @Override
    public int getStaminaEffect() {
        return super.getStaminaEffect() * -1;
    }

    /**
     * @return inverted value
     */
    @Override
    public int getAttackEffect() {
        return super.getAttackEffect() * -1;
    }

    /**
     * @return inverted value
     */
    @Override
    public int getDefenseEffect() {
        return super.getDefenseEffect() * -1;
    }

    @Override
    public String toString() {
        return super.toString().concat(getValue() != 0 ? "VAL " + getValue() : "");
    }

    @Override
    public int getValue() {
        return super.getValue() * -1;
    }
}
