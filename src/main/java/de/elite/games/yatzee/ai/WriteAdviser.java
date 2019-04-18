package de.elite.games.yatzee.ai;

import de.elite.games.yatzee.*;

import java.util.Arrays;
import java.util.List;

public class WriteAdviser {

    private final Roll roll;
    private final Board board;
    private final YatzeePlayer player;
    private final RollAnalyze rollAnalyze;

    public WriteAdviser(Roll roll, Board board, YatzeePlayer player, RollAnalyze rollAnalyze) {
        this.roll = roll;
        this.board = board;
        this.player = player;
        this.rollAnalyze = rollAnalyze;
    }

    public RowType getOptimalRow() {

        if (canSetInto(RowType.YATZEE)) {
            return RowType.YATZEE;
        }

        if (isYatzee()) {
            //TODO
        }

        //bonus sichern
        for (RowType rowType : Board.getTopRowTypes()) {
            if (isBonusFirming(rowType)) {
                return rowType;
            }
        }

        //the hard ones first
        if (canSetInto(RowType.MAJOR_STRAIGHT)) {
            return RowType.MAJOR_STRAIGHT;
        }
        if (canSetInto(RowType.FOUR_OF_A_KIND) && rollAnalyze.getHighestEyeOfIdenticals() >= 3) {
            return RowType.FOUR_OF_A_KIND;
        }
        if (canSetInto(RowType.THREE_OF_A_KIND) && rollAnalyze.getHighestEyeOfIdenticals() >= 4) {
            return RowType.THREE_OF_A_KIND;
        }
        if (canSetInto(RowType.TWO_PAIR) && hasMorePoints(RowType.TWO_PAIR, 23)) {
            return RowType.TWO_PAIR;
        }
        if (canSetInto(RowType.ONE_PAIR) && hasMorePoints(RowType.ONE_PAIR, 23)) {
            return RowType.ONE_PAIR;
        }

        //dann die topRows, falls drei gleich
        for (RowType rowType : Board.getTopRowTypes()) {
            if (isThreeOf(rowType)) {
                return rowType;
            }
        }

        //the easy ones
        if (canSetInto(RowType.MINOR_STRAIGHT)) {
            return RowType.MINOR_STRAIGHT;
        }
        if (canSetInto(RowType.FOUR_OF_A_KIND)) {
            return RowType.FOUR_OF_A_KIND;
        }
        if (canSetInto(RowType.FULL_HOUSE)) {
            return RowType.FULL_HOUSE;
        }
        if (canSetInto(RowType.THREE_OF_A_KIND)) {
            return RowType.THREE_OF_A_KIND;
        }
        if (canSetInto(RowType.TWO_PAIR)) {
            return RowType.TWO_PAIR;
        }
        if (canSetInto(RowType.ONE_PAIR)) {
            return RowType.ONE_PAIR;
        }
        //from now on we have to either purge or write baddies

        //jetzt halt zweier eintragen:
        for (RowType rowType : Board.getTopRowTypes()) {
            if (isTwoOf(rowType)) {
                return rowType;
            }
        }

        //1er zweier  bei single
        if (canSetInto(RowType.ONE)) {
            return RowType.ONE;
        }
        if (canSetInto(RowType.TWO)) {
            return RowType.TWO;
        }

        if (canSetInto(RowType.CHANCE) && hasMorePoints(RowType.ONE_PAIR, 20)) {
            return RowType.CHANCE;
        }

        if (canSetInto(RowType.FOUR_OF_A_KIND)) {
            return RowType.FOUR_OF_A_KIND;
        }
        if (canSetInto(RowType.MAJOR_STRAIGHT)) {
            return RowType.MAJOR_STRAIGHT;
        }

        for (RowType rowType : Board.getTopRowTypes()) {
            if (isOneOf(rowType)) {
                return rowType;
            }
        }

        //before we cancel something we write it into chance...
        if (canSetInto(RowType.CHANCE)) {
            return RowType.CHANCE;
        }

        List<RowType> bottomCancelList = getCancelList();
        for (RowType cancelType : bottomCancelList) {
            if (board.getRow(cancelType, player).isEmpty()) {
                return cancelType;
            }
        }
        List<RowType> topCancelList = Board.getTopRowTypes();
        for (RowType cancelType : topCancelList) {
            if (board.getRow(cancelType, player).isEmpty()) {
                return cancelType;
            }
        }

        throw new IllegalStateException("could not find any free row to write into");
    }

    private List<RowType> getCancelList() {
        return Arrays.asList(
                RowType.FOUR_OF_A_KIND,
                RowType.MAJOR_STRAIGHT,
                RowType.THREE_OF_A_KIND,
                RowType.MINOR_STRAIGHT,
                RowType.YATZEE,
                RowType.FULL_HOUSE,
                RowType.TWO_PAIR,
                RowType.ONE_PAIR
        );
    }

    private boolean hasMorePoints(RowType rowType, int amount) {
        return RollCalculator.getValue(rowType, roll) > amount;
    }

    private boolean isOneOf(RowType rowType) {
        return isNOf(rowType, 1);
    }

    private boolean isTwoOf(RowType rowType) {
        return isNOf(rowType, 2);
    }

    private boolean isThreeOf(RowType rowType) {
        return isNOf(rowType, 3);
    }

    private boolean isBonusFirming(RowType rowType) {
        return isNOf(rowType, 4);
    }

    private boolean isNOf(RowType rowType, int n) {
        int highestInCommon = rollAnalyze.getHighestEyeOfIdenticals();
        boolean hasN = canSetInto(rowType) && rollAnalyze.getAmountOfIdenticals() == n;
        return rowType.getName().equalsIgnoreCase("" + highestInCommon) && hasN;
    }


    private boolean isYatzee() {
        return RollCalculator.getValue(RowType.YATZEE, roll) > 0;
    }


    //FIXME better name, 'can' means is it wise to set here
    private boolean canSetInto(RowType rowType) {
        if (board.getRow(rowType, player).isEmpty()) {
            return RollCalculator.getValue(rowType, roll) > 0;
        }
        return false;
    }

}
