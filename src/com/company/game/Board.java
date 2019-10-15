package com.company.game;

import com.company.game.cards.*;
import com.company.game.enums.MagicType;
import com.company.game.players.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private final List<MonsterCard>[] monsterPiles;
    private final GameEngine gameEngine;
    private final RoundCounter roundCounter;
    private final Player[] players;

    public Board(RoundCounter roundCounter, Player[] players) {
        monsterPiles = new List[]{new ArrayList<MonsterCard>(), new ArrayList<MonsterCard>()};
        gameEngine = new GameEngine(players, roundCounter);
        this.roundCounter = roundCounter;
        this.players = players;
    }


    public List<String> getMonsterPile(int player) {
        if (player != 0 && player != 1) return null;

        return monsterPiles[player].stream().map(card -> card.toString()).collect(Collectors.toList());

    }

    public boolean placeMonsterOnBoard(MonsterCard monster) {
        return monsterPiles[roundCounter.getTurn()].add(monster);
    }

    public boolean placeEffectOnMonsterWithId(EffectCard effect, int id) {
        boolean cardPlaced = false;

        if (effect instanceof BuffCard) {
            if (monsterPiles[roundCounter.getTurn()].stream().anyMatch(card -> card.getId() == id)) {
                monsterPiles[roundCounter.getTurn()].stream().filter(card -> card.getId() == id).findFirst().get().setBuffCard((BuffCard) effect);
                cardPlaced = true;
            } else {
                cardPlaced = false;
            }
        } else if (effect instanceof DebuffCard) {
            if (monsterPiles[roundCounter.getOpponentIndex()].stream().anyMatch(card -> card.getId() == id)) {
                monsterPiles[roundCounter.getOpponentIndex()].stream().filter(card -> card.getId() == id).findFirst().get().setDebuffCard((DebuffCard) effect);
                cardPlaced = true;
            } else {
                cardPlaced = false;
            }

        }
        return cardPlaced;
    }

    public boolean attackPlayerWithMonster(int attacker) {
        return false;
    }

    public boolean attackMonsterWithMonster(int target, int attacker) {
        boolean didAttack = false;
        Optional<MonsterCard> optTargetCard = getOpponentMonsterPile()
                .stream()
                .filter(card -> card.getId() == target)
                .findFirst();

        Optional<MonsterCard> optAttackerCard = getCurrentPlayerMonsterPile()
                .stream()
                .filter(card -> card.getId() == attacker && card.getCalculatedStamina() > 0)
                .findFirst();

        if (optTargetCard.isPresent() && optAttackerCard.isPresent()) {
            MonsterCard attackerCard = optAttackerCard.get();
            MonsterCard targetCard = optTargetCard.get();

            didAttack = (getOpponentMonsterPile().remove(targetCard) && getCurrentPlayerMonsterPile().remove(attackerCard));

            MonsterCard[] engagedCards = gameEngine.engage(targetCard, attackerCard);

            if (engagedCards[0] != null) {
                getOpponentMonsterPile().add(engagedCards[0]);
            }
            if (engagedCards[1] != null) {
                getCurrentPlayerMonsterPile().add(engagedCards[1]);
            }

        }
        return didAttack;
    }

    private List<MonsterCard> getCurrentPlayerMonsterPile() {
        return monsterPiles[roundCounter.getTurn()];
    }

    private List<MonsterCard> getOpponentMonsterPile() {
        return monsterPiles[roundCounter.getOpponentIndex()];
    }

    public boolean useMagicOnMonster(MagicCard magicCard, int target) {
        if (magicCard == null || target <= 0 || !magicCard.isTargeted()) return false;

        boolean wasMagicUsed = false;
        Optional<MonsterCard> targetCard;


        switch (magicCard.getMagicType()) {
            case HEAL_CARD:
            case REMOVE_DEBUFF:
                targetCard = getCurrentPlayerMonsterPile().stream().filter(card -> card.getId() == target).findFirst();
                if (targetCard.isPresent()) {
                    getCurrentPlayerMonsterPile().remove(targetCard.get());
                    List<MonsterCard> affectedCards = gameEngine.engage(magicCard, new ArrayList<>(Arrays.asList(targetCard.get())));
                    getCurrentPlayerMonsterPile().addAll(affectedCards);
                    wasMagicUsed = true;
                }
                break;
            case STUN:
            case ATTACK_CARD:
            case REMOVE_BUFF:
                targetCard = getOpponentMonsterPile().stream().filter(card -> card.getId() == target).findFirst();
                if (targetCard.isPresent()) {
                    getOpponentMonsterPile().remove(targetCard.get());
                    List<MonsterCard> affectedCards = gameEngine.engage(magicCard, new ArrayList<>(Arrays.asList(targetCard.get())));
                    getOpponentMonsterPile().addAll(affectedCards);
                    wasMagicUsed = true;
                }
                break;
            case ATTACK_PLAYER:
            case HEAL_PLAYER:
            default:
                return false;
        }
        return wasMagicUsed;
    }

    public boolean useMagic(MagicCard magicCard) {
        boolean wasMagicUsed = false;
        List<MonsterCard> targetCards;
        List<MagicType> magicTypesTargetsPlayer = Arrays.asList(MagicType.HEAL_PLAYER, MagicType.ATTACK_PLAYER);

        if (magicCard != null
                && (!magicCard.isTargeted()
                || magicTypesTargetsPlayer.contains(magicCard.getMagicType()))) {
            switch (magicCard.getMagicType()) {
                case HEAL_CARD:
                case REMOVE_DEBUFF:
                    targetCards = getCurrentPlayerMonsterPile();
                    getCurrentPlayerMonsterPile().clear();
                    getCurrentPlayerMonsterPile().addAll(gameEngine.engage(magicCard, targetCards));
                    wasMagicUsed = true;
                    break;
                case STUN:
                case ATTACK_CARD:
                case REMOVE_BUFF:
                    targetCards = getOpponentMonsterPile();
                    getOpponentMonsterPile().clear();
                    getOpponentMonsterPile().addAll(gameEngine.engage(magicCard, targetCards));
                    wasMagicUsed = true;
                    break;
                case ATTACK_PLAYER:
                case HEAL_PLAYER:
                    wasMagicUsed = gameEngine.engage(magicCard);
                    break;
                default:
                    return false;
            }
        }
        return wasMagicUsed;
    }

    public void nextRound() {
        getCurrentPlayerMonsterPile().stream().forEach(MonsterCard::resetFatigue);
        players[roundCounter.getTurn()].drawFromDeckToHand();
        roundCounter.nextTurn();
    }
}
