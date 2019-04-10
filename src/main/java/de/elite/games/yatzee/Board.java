package de.elite.games.yatzee;

import java.util.*;

class Board {

    private final Map<YatzeePlayer, List<Row>> board = new HashMap<>();

    Board(List<YatzeePlayer> players) {
        for (YatzeePlayer player : players) {
            board.put(player, createRows());
        }
    }

    static List<RowType> getTopRowTypes() {
        List<RowType> topRowTypes = new ArrayList<>();
        topRowTypes.add(RowType.ONE);
        topRowTypes.add(RowType.TWO);
        topRowTypes.add(RowType.THREE);
        topRowTypes.add(RowType.FOUR);
        topRowTypes.add(RowType.FIVE);
        topRowTypes.add(RowType.SIX);
        return topRowTypes;
    }


    static List<RowType> getBottomRowTypes() {
        List<RowType> topRowTypes = new ArrayList<>();
        topRowTypes.add(RowType.ONE_PAIR);
        topRowTypes.add(RowType.TWO_PAIR);
        topRowTypes.add(RowType.THREE_OF_A_KIND);
        topRowTypes.add(RowType.FOUR_OF_A_KIND);
        topRowTypes.add(RowType.FULL_HOUSE);
        topRowTypes.add(RowType.MINOR_STRAIGHT);
        topRowTypes.add(RowType.MAJOR_STRAIGHT);
        topRowTypes.add(RowType.YATZEE);
        topRowTypes.add(RowType.CHANCE);
        return topRowTypes;
    }

    private List<Row> createRows() {
        return Arrays.asList(
                new Row(RowType.ONE),
                new Row(RowType.TWO),
                new Row(RowType.THREE),
                new Row(RowType.FOUR),
                new Row(RowType.FIVE),
                new Row(RowType.SIX),
                new Row(RowType.TOP_SUM),
                new Row(RowType.TOP_BONUS),
                new Row(RowType.ONE_PAIR),
                new Row(RowType.TWO_PAIR),
                new Row(RowType.THREE_OF_A_KIND),
                new Row(RowType.FOUR_OF_A_KIND),
                new Row(RowType.FULL_HOUSE),
                new Row(RowType.MINOR_STRAIGHT),
                new Row(RowType.MAJOR_STRAIGHT),
                new Row(RowType.YATZEE),
                new Row(RowType.CHANCE),
                new Row(RowType.BOTTOM_SUM)
        );
    }

    List<Row> getTopRows(YatzeePlayer player) {
        List<Row> topRows = new ArrayList<>();
        getTopRowTypes().forEach(t -> topRows.add(getRow(t, player)));
        return topRows;
    }

    List<Row> getBottomRows(YatzeePlayer player) {
        List<Row> bottomRows = new ArrayList<>();
        getBottomRowTypes().forEach(t -> bottomRows.add(getRow(t, player)));
        return bottomRows;
    }

    Row getRow(RowType rowType, List<Row> rows) {
        return rows.stream().filter(r -> rowType == r.getType()).findFirst().get();
    }

    Row getRow(RowType type, YatzeePlayer player) {
        List<Row> rows = board.get(player);
        return getRow(type, rows);
    }

    int getTopBonus(YatzeePlayer player) {
        int sum = getTopRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
        if (sum > 63) {
            return 35;
        }
        return 0;
    }

    int getTopSum(YatzeePlayer player) {
        int sum = getTopRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
        int bonus = getTopBonus(player);
        return sum + bonus;
    }

    int getBottomSum(YatzeePlayer player) {
        return getBottomRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
    }

    int getTotal(YatzeePlayer player) {
        int top = getTopSum(player);
        int bottom = getBottomSum(player);
        return top + bottom;
    }

    void write(YatzeePlayer player, RowType rowType, Roll currentRoll) {
        Row row = getRow(rowType, player);
        row.setRoll(currentRoll);
    }

    boolean hasRowsLeft(YatzeePlayer currentPlayer) {
        long topCount = getTopRows(currentPlayer).stream().filter(Row::isEmpty).count();
        if (topCount > 0) {
            return true;
        }
        long bottomCount = getBottomRows(currentPlayer).stream().filter(Row::isEmpty).count();
        if (bottomCount > 0) {
            return true;
        }
        return false;
    }

    List<RowType> getTopOpenRowTypes(YatzeePlayer currentPlayer) {
        List<RowType> topRowTypes = new ArrayList<>();
        for (Row row : getTopRows(currentPlayer)) {
            if (row.isEmpty()) {
                topRowTypes.add(row.getType());
            }
        }
        return topRowTypes;
    }

    List<RowType> getBottomOpenRowTypes(YatzeePlayer currentPlayer) {
        List<RowType> bottomRowTypes = new ArrayList<>();
        for (Row row : getBottomRows(currentPlayer)) {
            if (row.isEmpty()) {
                bottomRowTypes.add(row.getType());
            }
        }
        return bottomRowTypes;
    }

}
