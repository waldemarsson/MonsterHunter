package com.company.game.factories;


import com.company.game.cards.EffectCard;
import com.company.game.enums.EffectType;

public class EffectCardFactory {

    EffectType[] effects;

    public EffectCardFactory() {
        effects = new EffectType[]{EffectType.ATTACK, EffectType.DEFENSE, EffectType.STAMINA};
    }


    /**
     * @param isBuff
     * @return  BuffCard / DebuffCard depending on isBuff
     */
    public EffectCard buildEffectCard(boolean isBuff) {
        return null;
    }

    /**
     * @return 1 or 2
     */
    private int getRandomValue() {
        return 0;
    }


    /**
     * @return one EffectType from effects (random)
     */
    private EffectType getRandomEffect() {
        return null;
    }

}
