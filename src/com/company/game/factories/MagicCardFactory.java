package com.company.game.factories;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;

import java.util.concurrent.ThreadLocalRandom;

public class MagicCardFactory {

    public MagicCardFactory() {
    }

    public MagicCard buildCard(int id) {
        return new MagicCard(id, getRandomMagicType(), isTargeted(), getRandomValue());
    }

    /**
     * @return 3 - 5
     */
    private int getRandomValue() {
        return ThreadLocalRandom.current().nextInt(3, 6);
    }

    private boolean isTargeted() {
        return getRandomValue() == 3 ? true : false;
    }


    private MagicType getRandomMagicType() {
        return MagicType.values()[(ThreadLocalRandom.current().nextInt(0, MagicType.values().length))];
    }

}
