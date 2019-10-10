package com.company.game.cards;

import com.company.game.enums.MagicType;

public class MagicCard {

    private final MagicType magicType;
    private final boolean targeted;
    private final int value;

    public MagicCard(MagicType magicType, boolean targeted, int value) {
        this.magicType = magicType;
        this.targeted = targeted;
        this.value = value;
    }

    public MagicType getMagicType() {
        return magicType;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return null;
    }
}
