package com.company.game.cards;

import com.company.game.enums.MagicType;

public class MagicCard extends Card {

    private final MagicType magicType;
    private final boolean targeted;
    private final int value;

    public MagicCard(int id, MagicType magicType, boolean targeted, int value) {
        super(id, targeted ? magicType.getLabel() : magicType.getLabel() + "_BOMB");
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
        String str = String.format("%1$-25s", value > 0 ? super.toString() : super.toString().replaceFirst(":", " "));
        if (value > 0) str = str.concat("VAL  " + value);
        return str;
    }
}
