package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.Board;
import com.github.martinfrank.yahtzee.RowType;
import com.github.martinfrank.yahtzee.YahtzeePlayer;

import java.util.List;

public class BoardAnalyze {

    private final Board board;
    private final YahtzeePlayer player;
    private boolean hasBonus;

    public BoardAnalyze(Board board, YahtzeePlayer player) {
        this.board = board;
        this.player = player;
        analyzeBonus(board, player);
    }

    private void analyzeBonus(Board board, YahtzeePlayer player) {
        int sum = board.getTopSum(player);
        if (sum >= 63) {
            hasBonus = true;
        } else {
            hasBonus = false;
        }
    }

    public boolean hasBonus() {
        return hasBonus;
    }

    boolean isTopRowEmpty(int row) {
        List<RowType> topRows = Board.getTopRowTypes();
        for (RowType type : topRows) {
            if (type.getName().equalsIgnoreCase("" + row)) {
                return board.getRow(type, player).isEmpty();
            }
        }
        return false;
    }

    public boolean hasBottomRowsWithVariableCounter() {
        for (RowType type : Board.getBottomRowWithVariableCounterTypes()) {
            if (board.getRow(type, player).isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
