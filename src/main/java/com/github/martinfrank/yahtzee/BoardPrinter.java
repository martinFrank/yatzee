package com.github.martinfrank.yahtzee;

import com.github.martinfrank.boardgamelib.Player;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardPrinter {

    private static final int DESCRIPTION_COLUMN_LENGTH = 8;
    private static final int NAME_COLUMN_LENGTH = 5;

    private BoardPrinter() {

    }

    public static void print(PrintStream out, YahtzeeGame yahtzeeGame) {
        Board board = yahtzeeGame.getBoard();
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        out.println(getPlayerNameLine(yahtzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        for (RowType type : Board.getTopRowTypes()) {
            out.println(getRowLine(type, board, yahtzeeGame.getPlayers()));
        }
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        out.println(getTopSumRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getBonusRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        out.println(getTopTotalRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        for (RowType type : Board.getBottomRowTypes()) {
            out.println(getRowLine(type, board, yahtzeeGame.getPlayers()));
        }
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        out.println(getBottomSumRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getTopSumRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
        out.println(getTotalRowLine(board, yahtzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yahtzeeGame.getPlayers()));
    }

    private static String getBonusRowLine(Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_BONUS));
        for (YahtzeePlayer player : players) {
            String topBonus = "" + board.getTopBonus(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTopSumRowLine(Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_SUM));
        for (YahtzeePlayer player : players) {
            String topSum = "" + board.getTopSum(player);
            sb.append(fitInName(topSum));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTopTotalRowLine(Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_TOTAL));
        for (YahtzeePlayer player : players) {
            String topSum = "" + board.getTopTotal(player);
            sb.append(fitInName(topSum));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getBottomSumRowLine(Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.BOTTOM_SUM));
        for (YahtzeePlayer player : players) {
            String topBonus = "" + board.getBottomSum(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTotalRowLine(Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOTAL));
        for (YahtzeePlayer player : players) {
            String topBonus = "" + board.getTotal(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getRowLine(RowType type, Board board, List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(type));
        for (YahtzeePlayer player : players) {
            Row playersRow = board.getRow(type, player);
            sb.append(getRowValue(playersRow));
        }
        return sb.toString();
    }

    private static String getRowValue(Row row) {
        return fitInName(getValueAsString(row)) + "|";
    }

    private static String getValueAsString(Row row) {
        int value = row.getValue();
        if (row.isEmpty()) {
            return "";
        } else {
            if (value == 0) {
                return "X";
            } else {
                return "" + row.getValue();
            }
        }
    }

    private static String getRowTypeNameLine(RowType rowType) {
        return "|" + fitInDescription(rowType.getName()) + "|";
    }

    private static String fitInName(String string) {
        return fitInLength(string, NAME_COLUMN_LENGTH);
    }

    private static String fitInDescription(String string) {
        return fitInLength(string, DESCRIPTION_COLUMN_LENGTH);
    }

    private static String fitInLength(String string, int length) {
        int front = (length - string.length()) / 2;
        int trail = length - string.length() - front;
        return createLinePart(front, " ") + string + createLinePart(trail, " ");

    }

    private static String getPlayerNameLine(List<YahtzeePlayer> players) {
        StringBuilder sb = new StringBuilder("|");
        sb.append(fitInDescription("Player"));
        sb.append("|");
        for (Player player : players) {
            sb.append(fitInName(player.getName()));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getHorizontalSeparator(List<YahtzeePlayer> players) {
        String crossCharacter = "+";
        String lineCharacter = "-";
        StringBuilder sb = new StringBuilder(crossCharacter);
        sb.append(createLinePart(DESCRIPTION_COLUMN_LENGTH, lineCharacter));
        for (int i = 0; i < players.size(); i++) {
            sb.append(crossCharacter);
            sb.append(createLinePart(NAME_COLUMN_LENGTH, lineCharacter));

        }
        sb.append(crossCharacter);
        return sb.toString();
    }

    private static String createLinePart(int i, String character) {
        return IntStream.range(0, i).mapToObj(c -> character).collect(Collectors.joining());
    }
}
