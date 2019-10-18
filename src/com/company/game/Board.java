package com.company.game;

import com.company.game.cards.*;
import com.company.game.collections.Hand;
import com.company.game.enums.MagicType;
import com.company.game.players.Player;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return monsterPiles[player].stream().map(card -> card.toString()).sorted().collect(Collectors.toList());
    }

    public boolean placeMonsterOnBoard(MonsterCard monster) {
        return monsterPiles[roundCounter.getTurn()].add(monster);
    }

    public boolean placeEffectOnMonsterWithId(EffectCard effect, int id) {
        if (effect == null || id <= 0) return false;

        boolean cardPlaced = false;
        Optional<MonsterCard> target;

        if (effect instanceof BuffCard) {
            target = getCurrentPlayerMonsterPile().stream().filter(monsterCard -> monsterCard.getId() == id).findFirst();
            if (target.isPresent()) {
                target.get().setBuffCard((BuffCard) effect);
                cardPlaced = true;
            }
        } else if (effect instanceof DebuffCard) {
            target = getOpponentMonsterPile().stream().filter(monsterCard -> monsterCard.getId() == id).findFirst();
            if (target.isPresent()) {
                target.get().setDebuffCard((DebuffCard) effect);
                cardPlaced = true;
            }
        }
        return cardPlaced;
    }

    public boolean attackPlayerWithMonster(int attacker) {
        if (attacker <= 0) return false;

        boolean didAttack = false;
        Optional<MonsterCard> optAttackerCard = getCurrentPlayerMonsterPile()
                .stream()
                .filter(card -> card.getId() == attacker && card.getCalculatedStamina() > 0)
                .findFirst();

        if (optAttackerCard.isPresent()) {
            didAttack = gameEngine.engage(optAttackerCard.get());
        }

        return didAttack;
    }

    public boolean attackMonsterWithMonster(int target, int attacker) {
        if (target <= 0 || attacker <= 0) return false;

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

            if (didAttack) {
                MonsterCard[] engagedCards = gameEngine.engage(targetCard, attackerCard);

                if (engagedCards[0] != null) {
                    didAttack = getOpponentMonsterPile().add(engagedCards[0]);
                }
                if (engagedCards[1] != null) {
                    didAttack = getCurrentPlayerMonsterPile().add(engagedCards[1]);
                }
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
                    MonsterCard card = targetCard.get();
                    getCurrentPlayerMonsterPile().remove(card);
                    List<MonsterCard> affectedCards = gameEngine.engage(magicCard, new ArrayList<>(Arrays.asList(card)));
                    getCurrentPlayerMonsterPile().addAll(affectedCards);
                    wasMagicUsed = true;
                }
                break;
            case STUN:
            case ATTACK_CARD:
            case REMOVE_BUFF:
                targetCard = getOpponentMonsterPile().stream().filter(card -> card.getId() == target).findFirst();
                if (targetCard.isPresent()) {
                    MonsterCard card = targetCard.get();
                    getOpponentMonsterPile().remove(card);
                    List<MonsterCard> affectedCards = gameEngine.engage(magicCard, new ArrayList<>(Arrays.asList(card)));
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
                    targetCards = getCurrentPlayerMonsterPile().stream().collect(Collectors.toList());
                    getCurrentPlayerMonsterPile().clear();
                    getCurrentPlayerMonsterPile().addAll(gameEngine.engage(magicCard, targetCards));
                    wasMagicUsed = true;
                    break;
                case STUN:
                case ATTACK_CARD:
                case REMOVE_BUFF:
                    targetCards = getOpponentMonsterPile().stream().collect(Collectors.toList());
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

    public Card getShowCard(int id){
        Card card = null;
        if(players[roundCounter.getTurn()].getHand().hasCard(id)){
            card = players[roundCounter.getTurn()].getHand().playCard(id);
            players[roundCounter.getTurn()].getHand().putCard(card);
        } else {
            Optional<MonsterCard> optionalCard = Stream.of(monsterPiles[0], monsterPiles[1]).flatMap(Collection::stream).filter(c -> c.getId() == id).findFirst();
            if(optionalCard.isPresent())
                card = optionalCard.get();
        }
        return card;
    }
}
