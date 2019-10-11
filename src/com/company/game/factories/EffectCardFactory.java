package com.company.game.factories;


import com.company.game.cards.BuffCard;
import com.company.game.cards.DebuffCard;
import com.company.game.cards.EffectCard;
import com.company.game.enums.EffectType;

import java.util.concurrent.ThreadLocalRandom;

public class EffectCardFactory {

    private final EffectType[] effects;

    public EffectCardFactory() {
        effects = new EffectType[]{EffectType.ATTACK, EffectType.DEFENSE, EffectType.STAMINA};
    }


    /**
     * @param isBuff
     * @return  BuffCard / DebuffCard depending on isBuff
     */
    public EffectCard buildEffectCard(int id, boolean isBuff) {
        return isBuff ? new BuffCard(id, getRandomValue(), getRandomEffect()) :
                new DebuffCard(id, getRandomValue(), getRandomEffect());
    }

    /**
     * @return 1 or 2
     */
    private int getRandomValue() {
        return ThreadLocalRandom.current().nextInt(1, 3);
    }


    /**
     * @return one EffectType from effects (random)
     */
    private EffectType getRandomEffect() {
        return effects[ThreadLocalRandom.current().nextInt(0, effects.length)];
    }

}
