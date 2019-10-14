package com.company.game;

import com.company.game.cards.*;
import com.company.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public boolean attackMonsterWithMonster(int defender, int attacker) {
        return false;
    }

    public boolean useMagicOnMonster(MagicCard magicCard, int monsterCard) {
        return false;
    }

    public boolean useMagic(MagicCard magicCard) {

        return false;
    }
}
