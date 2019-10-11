package com.company.game.cards;

import com.company.game.enums.EffectType;

public class DebuffCard extends EffectCard {

    public DebuffCard(int id, String name, int value, EffectType effectType) {
        super(id, name, value, effectType);
    }


    /**
     * @return inverted value
     */
    @Override
    public int getStaminaEffect() {
        return super.getStaminaEffect();
    }

    /**
     * @return inverted value
     */
    @Override
    public int getHealthEffect() {
        return super.getHealthEffect();
    }

    /**
     * @return inverted value
     */
    @Override
    public int getAttackEffect() {
        return super.getAttackEffect();
    }

    /**
     * @return inverted value
     */
    @Override
    public int getDefenseEffect() {
        return super.getDefenseEffect();
    }

    @Override
    public String toString() {
        return "DEBUFFCARD";
    }
}
