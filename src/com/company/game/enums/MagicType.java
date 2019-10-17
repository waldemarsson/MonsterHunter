package com.company.game.enums;

public enum MagicType {
    STUN("STUN", "FATIGUE", false),
    HEAL_CARD("HEALING", "HP", true),
    HEAL_PLAYER("SPA_TREATMENT", "HP", true),
    ATTACK_CARD("FIREBALL", "HP", false),
    ATTACK_PLAYER("ADULT_BULLYING", "HP", false),
    REMOVE_BUFF("CRITIQUE", "BUFF", true),
    REMOVE_DEBUFF("CLEANSE", "DEBUFF", false);

    final String label;
    final String affectedField;
    final boolean onSelf;

    public boolean isOnSelf() {
        return onSelf;
    }

    MagicType(String label, String affectedField, boolean onSelf) {
        this.label = label;
        this.affectedField = affectedField;
        this.onSelf = onSelf;
    }

    public String getLabel(){
        return this.label;
    }

    public String getAffectedField() {
        return affectedField;
    }
}
