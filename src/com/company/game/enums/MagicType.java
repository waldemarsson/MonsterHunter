package com.company.game.enums;

public enum MagicType {
    STUN("STUN"),
    HEAL_CARD("HEALING"),
    HEAL_PLAYER("SPA_TREATMENT"),
    ATTACK_CARD("FIREBALL"),
    ATTACK_PLAYER("ADULT_BULLYING"),
    REMOVE_BUFF("CRITIQUE"),
    REMOVE_DEBUFF("CLEANSE");

    final String label;

    MagicType(String label) {
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
