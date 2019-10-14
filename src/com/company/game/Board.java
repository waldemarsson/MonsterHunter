package com.company.game;

import com.company.game.cards.*;
import com.company.game.players.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final List<MonsterCard>[] monsterPiles;
    private final GameEngine gameEngine;
    private final RoundCounter roundCounter;

    public Board(RoundCounter roundCounter, Player[] players) {
        monsterPiles = new List[]{new ArrayList<MonsterCard>(), new ArrayList<MonsterCard>()};
        gameEngine = new GameEngine(players, roundCounter);
        this.roundCounter = roundCounter;
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

    public boolean attackMonsterWithMonster(int target, int attacker) {
        boolean didAttack = false;
        if (getCurrentPlayerMonsterPile()
                .stream()
                .anyMatch(card -> card.getId() == attacker)
                && getOpponentMonsterPile()
                .stream()
                .anyMatch(card -> card.getId() == target)) {

            Optional<MonsterCard> targetCard = getOpponentMonsterPile()
                    .stream()
                    .filter(card -> card.getId() == target)
                    .findFirst();

            Optional<MonsterCard> attackerCard = getCurrentPlayerMonsterPile()
                    .stream()
                    .filter(card -> card.getId() == attacker)
                    .findFirst();

            if (targetCard.isPresent() && attackerCard.isPresent()) {
                getOpponentMonsterPile().remove(targetCard);
                getCurrentPlayerMonsterPile().remove(attackerCard);

                MonsterCard[] engagedCards = gameEngine.engage(targetCard.get(), attackerCard.get());

                if (engagedCards[0] != null) {
                    getOpponentMonsterPile().add(engagedCards[0]);
                }

                if (engagedCards[1] != null) {
                    getCurrentPlayerMonsterPile().add(engagedCards[1]);
                }

                didAttack = true;
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
        boolean wasMagicUsed = false;
        int whatMonsterPile = -1;

        if (magicCard != null
                && (getCurrentPlayerMonsterPile().stream().anyMatch(card -> card.getId() == target)
                || getOpponentMonsterPile().stream().anyMatch(card -> card.getId() == target)
        )) {
            List<Card> targetCard = null;

            if (getCurrentPlayerMonsterPile().stream().anyMatch(card -> card.getId() == target)) {
                whatMonsterPile = roundCounter.getTurn();
                targetCard = getCurrentPlayerMonsterPile().stream().filter(card -> card.getId() == target).collect(Collectors.toList());
                getCurrentPlayerMonsterPile().remove(targetCard.get(0));
            }

            if (getOpponentMonsterPile().stream().anyMatch(card -> card.getId() == target)) {
                whatMonsterPile = roundCounter.getTurn();
                targetCard = getOpponentMonsterPile().stream().filter(card -> card.getId() == target).collect(Collectors.toList());
                getOpponentMonsterPile().remove(targetCard.get(0));
            }

//            List<Card> targetCard = Stream.of(monsterPiles[0], monsterPiles[1]).flatMap(Collection::stream).filter(card -> card.getId() == target).collect(Collectors.toList());
            if (targetCard.size() == 1 && whatMonsterPile >= 0) {
                Card[] affectedCards = gameEngine.engage(magicCard, targetCard);
                int finalWhatMonsterPile = whatMonsterPile;
                Stream.of(affectedCards).map(card -> monsterPiles[finalWhatMonsterPile].add((MonsterCard) card));
                wasMagicUsed = true;
            }
        }
        return wasMagicUsed;
    }

    public boolean useMagic(MagicCard magicCard) {

        return false;
    }
}
