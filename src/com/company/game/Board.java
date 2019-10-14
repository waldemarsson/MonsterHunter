package com.company.game;

import com.company.game.cards.Card;
import com.company.game.cards.EffectCard;
import com.company.game.cards.MagicCard;
import com.company.game.cards.MonsterCard;
import com.company.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private final List[] monsterPiles;
    private final GameEngine gameEngine;
    private final RoundCounter roundCounter;

    public Board(RoundCounter roundCounter, Player[] players) {
        monsterPiles = new List[]{new ArrayList<MonsterCard>(), new ArrayList<MonsterCard>()};
        gameEngine = new GameEngine(players, roundCounter);
        this.roundCounter = roundCounter;
    }


    public List<String> getMonsterPile(int player) {
        if (player != 0 && player != 1) return null;

        return (List<String>) monsterPiles[player].stream().map(card -> card.toString()).collect(Collectors.toList());

    }

    public boolean placeMonsterOnBoard(MonsterCard monster) {
        return monsterPiles[roundCounter.getTurn()].add(monster);
    }

    public boolean placeEffectOnMonsterWithId(EffectCard effect, int id) {
        return false;
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
