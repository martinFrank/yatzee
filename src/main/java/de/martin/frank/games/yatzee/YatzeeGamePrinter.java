package de.martin.frank.games.yatzee;

import de.frank.martin.games.boardgamelib.Player;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.martin.frank.games.yatzee.YatzeeUtil.*;

class YatzeeGamePrinter {

    private final YatzeeGame yatzeeGame;

    YatzeeGamePrinter(YatzeeGame yatzeeGame) {
        this.yatzeeGame = yatzeeGame;
    }

    void printGame(PrintStream out) {
        out.println("+------+----+----+");
        out.println("|Player|"+getPlayers()+"|");
        out.println("+------+----+----+");
        out.println(getRows(YatzeeRowType.ONE));
        out.println(getRows(YatzeeRowType.TWO));
        out.println(getRows(YatzeeRowType.THREE));
        out.println(getRows(YatzeeRowType.FOUR));
        out.println(getRows(YatzeeRowType.FIVE));
        out.println(getRows(YatzeeRowType.SIX));
        out.println("|sum   |"+getTopSum());
        out.println("|bonus |"+getBonus());
        out.println("+------+----+----+");
        out.println("|1pair |"+getPlayersRow(YatzeeRowType.ONE_PAIR));
        out.println("|2pair |"+getPlayersRow(YatzeeRowType.TWO_PAIR));
        out.println("|3oak  |"+getPlayersRow(YatzeeRowType.THREE_OF_A_KIND));
        out.println("|4oak  |"+getPlayersRow(YatzeeRowType.FOUR_OF_A_KIND));
        out.println("|fh    |"+getPlayersRow(YatzeeRowType.FULL_HOUSE));
        out.println("|mistr |"+getPlayersRow(YatzeeRowType.MINOR_STRAIGHT));
        out.println("|mastr |"+getPlayersRow(YatzeeRowType.MAJOR_STRAIGHT));
        out.println("|yatzee|"+getPlayersRow(YatzeeRowType.YATZEE));
        out.println("|chance|"+getPlayersRow(YatzeeRowType.CHANCE));
        out.println("|sum   |"+getBottomSum()+"|");
        out.println("+================+");
        out.println("|sum   |"+getTotalSum()+"|");
        out.println("+------+----+----+");
        printThrows(out);
    }

    private String getPlayersRow(YatzeeRowType rowType) {
        StringBuilder sb = new StringBuilder();
        for(YatzeePlayer player: yatzeeGame.getPlayers()){
            Optional<YatzeeRow> row = getRow(yatzeeGame.getBoard().get(player), rowType);
            sb.append(formatShort(row.get().getValue()));
            sb.append("|");
        }
        return sb.toString();
    }

    private String getTopSum() {
        StringBuilder sb = new StringBuilder();
        for(YatzeePlayer player: yatzeeGame.getPlayers()){
            int topSum = yatzeeGame.getTopScore(player);
            sb.append(formatShort(Integer.toString(topSum)));
            sb.append("|");
        }
        return sb.toString();
    }

    private String getBonus() {
        StringBuilder sb = new StringBuilder();
        for(YatzeePlayer player: yatzeeGame.getPlayers()){
            int bonus = yatzeeGame.getBonusScore(player);
            String bonusString = areAllTopRowsSet(player)?bonus==0?"XX":Integer.toString(bonus):"?";
            sb.append(formatShort(bonusString));
            sb.append("|");
        }
        return sb.toString();
    }

    private boolean areAllTopRowsSet(YatzeePlayer player) {
        for (YatzeeRow row: getTopRows(yatzeeGame.getBoard().get(player))){
            if (row.getRoll() == null){
                return false;
            }
        }
        return true;
    }

    private String getBottomSum() {
        StringBuilder sb = new StringBuilder();
        for(YatzeePlayer player: yatzeeGame.getPlayers()){
            int topSum = yatzeeGame.getBottomScore(player);
            sb.append(formatShort(Integer.toString(topSum)));
            sb.append("|");
        }
        return sb.toString();
    }

    private String getTotalSum() {
        StringBuilder sb = new StringBuilder();
        for(YatzeePlayer player: yatzeeGame.getPlayers()){
            int topSum = yatzeeGame.getTopScore(player);
            int bottomSum = yatzeeGame.getBottomScore(player);
            int bonus = yatzeeGame.getBonusScore(player);
            sb.append(formatShort(Integer.toString(topSum+bottomSum+bonus)));
            sb.append("|");
        }
        return sb.toString();
    }

    private String getRows(YatzeeRowType rowType) {

        StringBuffer sb = new StringBuffer("|");
        sb.append(formatLong(rowType.getName()));
        sb.append("|");
        for(Player p: yatzeeGame.getPlayers()){
            sb.append(formatShort(getRowValue(yatzeeGame.getBoard().get(p), rowType)));
            sb.append("|");
        }
        return sb.toString();
    }

    private String getRowValue(List<YatzeeRow> yatzeeRows, YatzeeRowType rowType) {
        Optional<YatzeeRow> row =  getRow(yatzeeRows, rowType);
        if (row.isPresent()){
           return row.get().getValue();
        }
        return "";
    }

    private String getPlayers() {
        return yatzeeGame.getPlayers().stream().map(p -> formatShort(p.getName()))
                .collect(Collectors.joining("|"));
    }

    private void printThrows(PrintStream out) {
        YatzeeRoll[] currentThrows = yatzeeGame.getCurrentRolls();
        Boolean[][] currentKeeps = yatzeeGame.getCurrentKeeps();
        out.println("          1  2  3  4  5");
        out.println("throw #1 "+ getRoll(currentThrows[0])+"   "+getKeep(currentKeeps[0]));
        out.println("throw #2 "+ getRoll(currentThrows[1])+"   "+getKeep(currentKeeps[1]));
        out.println("throw #3 "+ getRoll(currentThrows[2]));
        out.println("you are at throw #"+yatzeeGame.getCurrentThrowIndex());
    }

    private String getKeep(Boolean[] currentKeep) {
        StringBuffer sb = new StringBuffer("[");
        sb.append(Arrays.stream(currentKeep).map(b->b==null?"r":b?"k":"r") .collect(Collectors.joining("][")));
        sb.append("]");
        return sb.toString();
    }

    private String getRoll(YatzeeRoll currentThrow) {
        return currentThrow == null?"[?][?][?][?][?]":currentThrow.toString();
    }

    void printResults(PrintStream out) {

    }
}
