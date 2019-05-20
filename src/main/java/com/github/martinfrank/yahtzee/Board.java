package com.github.martinfrank.yahtzee;

import java.util.*;

public class Board {

    private final Map<YahtzeePlayer, List<Row>> interalBoardRepresentation = new HashMap<>();

    Board(List<YahtzeePlayer> players) {
        for (YahtzeePlayer player : players) {
            interalBoardRepresentation.put(player, createRows());
        }
    }

    public static List<RowType> getTopRowTypes() {
        List<RowType> topRowTypes = new ArrayList<>();
        topRowTypes.add(RowType.ONE);
        topRowTypes.add(RowType.TWO);
        topRowTypes.add(RowType.THREE);
        topRowTypes.add(RowType.FOUR);
        topRowTypes.add(RowType.FIVE);
        topRowTypes.add(RowType.SIX);
        return topRowTypes;
    }


    public static List<RowType> getBottomRowTypes() {
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

    public static List<RowType> getBottomRowWithVariableCounterTypes() {
        List<RowType> topRowTypesWithVariableCounter = new ArrayList<>();
        topRowTypesWithVariableCounter.add(RowType.ONE_PAIR);
        topRowTypesWithVariableCounter.add(RowType.TWO_PAIR);
        topRowTypesWithVariableCounter.add(RowType.THREE_OF_A_KIND);
        topRowTypesWithVariableCounter.add(RowType.FOUR_OF_A_KIND);
        topRowTypesWithVariableCounter.add(RowType.CHANCE);
        return topRowTypesWithVariableCounter;
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
                new Row(RowType.TOP_TOTAL),
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

    List<Row> getTopRows(YahtzeePlayer player) {
        List<Row> topRows = new ArrayList<>();
        getTopRowTypes().forEach(t -> topRows.add(getRow(t, player)));
        return topRows;
    }

    List<Row> getBottomRows(YahtzeePlayer player) {
        List<Row> bottomRows = new ArrayList<>();
        getBottomRowTypes().forEach(t -> bottomRows.add(getRow(t, player)));
        return bottomRows;
    }

    Row getRow(RowType rowType, List<Row> rows) {
        return rows.stream().filter(r -> rowType == r.getType()).findFirst().orElse(null);
    }

    public Row getRow(RowType type, YahtzeePlayer player) {
        List<Row> rows = interalBoardRepresentation.get(player);
        return getRow(type, rows);
    }

    int getTopBonus(YahtzeePlayer player) {
        int sum = getTopRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
        if (sum > 63) {
            return 35;
        }
        return 0;
    }

    public int getTopSum(YahtzeePlayer player) {
        return getTopRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
    }

    public int getTopTotal(YahtzeePlayer player) {
        int sum = getTopRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
        int bonus = getTopBonus(player);
        return sum + bonus;
    }

    int getBottomSum(YahtzeePlayer player) {
        return getBottomRows(player).stream().filter(r -> !r.isEmpty()).mapToInt(Row::getValue).sum();
    }

    int getTotal(YahtzeePlayer player) {
        int top = getTopTotal(player);
        int bottom = getBottomSum(player);
        return top + bottom;
    }

    void write(YahtzeePlayer player, RowType rowType, Roll currentRoll) {
        Row row = getRow(rowType, player);
        row.setRoll(currentRoll);
    }

    boolean hasRowsLeft(YahtzeePlayer currentPlayer) {
        long topCount = getTopRows(currentPlayer).stream().filter(Row::isEmpty).count();
        if (topCount > 0) {
            return true;
        }
        long bottomCount = getBottomRows(currentPlayer).stream().filter(Row::isEmpty).count();
        return bottomCount > 0;
    }

    List<RowType> getTopOpenRowTypes(YahtzeePlayer currentPlayer) {
        List<RowType> topRowTypes = new ArrayList<>();
        for (Row row : getTopRows(currentPlayer)) {
            if (row.isEmpty()) {
                topRowTypes.add(row.getType());
            }
        }
        return topRowTypes;
    }

    List<RowType> getBottomOpenRowTypes(YahtzeePlayer currentPlayer) {
        List<RowType> bottomRowTypes = new ArrayList<>();
        for (Row row : getBottomRows(currentPlayer)) {
            if (row.isEmpty()) {
                bottomRowTypes.add(row.getType());
            }
        }
        return bottomRowTypes;
    }

    public boolean canWriteInto(RowType rowType, YahtzeePlayer player) {
        return getRow(rowType, player).isEmpty();
    }
}
