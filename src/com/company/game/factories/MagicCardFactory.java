package com.company.game.factories;

import com.company.game.cards.BuffCard;
import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MagicCardFactory {
    // STUN, HEAL_CARD, HEAL_PLAYER, ATTACK_CARD, ATTACK_PLAYER, REMOVE_BUFF, REMOVE_DEBUFF

    public MagicCardFactory() {
    }

    public MagicCard buildCard(int id) {
        return null;
    }


    /**
     * @return 3 - 5
     */
    private int getRandomValue() {
        return ThreadLocalRandom.current().nextInt(3, 6);
    }


    public MagicType getRandomMagicType() {
        List<MagicType> types = Arrays.asList(MagicType.ATTACK_CARD, MagicType.ATTACK_PLAYER, MagicType.HEAL_CARD, MagicType.HEAL_PLAYER, MagicType.REMOVE_BUFF, MagicType.REMOVE_DEBUFF, MagicType.STUN);
        return types.get(ThreadLocalRandom.current().nextInt(0, types.size()));
    }

}
