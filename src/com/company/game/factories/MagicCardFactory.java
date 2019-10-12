package com.company.game.factories;

import com.company.game.cards.MagicCard;
import com.company.game.enums.MagicType;

import java.util.concurrent.ThreadLocalRandom;

public class MagicCardFactory {

    private final MagicType[] magicTypes;

    public MagicCardFactory() {
        magicTypes = MagicType.values();
    }

    public MagicCard buildCard(int id) {
        MagicType magicType = getRandomMagicType();
        boolean targetsPlayer = magicType.name().contains("PLAYER");
        boolean isBuff = magicType.name().contains("BUFF");
        boolean targeted = targetsPlayer ? true : isTargeted();
        int value = isBuff ? 0 : getRandomValue();

        return new MagicCard(id, magicType, targeted, value);
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
        return magicTypes[(ThreadLocalRandom.current().nextInt(0, MagicType.values().length))];
    }

}
