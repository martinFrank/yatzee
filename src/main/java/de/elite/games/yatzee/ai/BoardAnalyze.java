package de.elite.games.yatzee.ai;

import de.elite.games.yatzee.Board;
import de.elite.games.yatzee.RowType;
import de.elite.games.yatzee.YatzeePlayer;

import java.util.List;

public class BoardAnalyze {

    private final Board board;
    private final YatzeePlayer player;
    private boolean hasBonus;

    public BoardAnalyze(Board board, YatzeePlayer player) {
        this.board = board;
        this.player = player;
        analyzeBonus(board, player);
    }

    private void analyzeBonus(Board board, YatzeePlayer player) {
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
