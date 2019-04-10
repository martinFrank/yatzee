package de.elite.games.yatzee;

import de.frank.martin.games.boardgamelib.BasePlayer;

public class YatzeePlayer extends BasePlayer<YatzeeGame> {

    public YatzeePlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {
        YatzeeGame yatzeeGame = getBoardGame();


        System.out.println("done Ai turn");
        yatzeeGame.endPlayersTurn();
        yatzeeGame.startPlayersTurn();
    }
}
