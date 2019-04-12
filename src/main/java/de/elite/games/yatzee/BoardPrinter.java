package de.elite.games.yatzee;

import de.frank.martin.games.boardgamelib.Player;

import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardPrinter {

    private static final int DESCRIPTION_COLUMN_LENGTH = 8;
    private static final int NAME_COLUMN_LENGTH = 5;

    public static void print(PrintStream out, YatzeeGame yatzeeGame) {
        Board board = yatzeeGame.getBoard();
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println(getPlayerNameLine(yatzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        for (RowType type : Board.getTopRowTypes()) {
            out.println(getRowLine(type, board, yatzeeGame.getPlayers()));
        }
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println(getTopSumRowLine(board, yatzeeGame.getPlayers()));
        out.println(getBonusRowLine(board, yatzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println(getTopTotalRowLine(board, yatzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        for (RowType type : Board.getBottomRowTypes()) {
            out.println(getRowLine(type, board, yatzeeGame.getPlayers()));
        }
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println(getBottomSumRowLine(board, yatzeeGame.getPlayers()));
        out.println(getTopSumRowLine(board, yatzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println(getTotalRowLine(board, yatzeeGame.getPlayers()));
        out.println(getHorizontalSeparator(yatzeeGame.getPlayers()));
        out.println();
    }

    private static String getBonusRowLine(Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_BONUS));
        for (YatzeePlayer player : players) {
            String topBonus = "" + board.getTopBonus(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTopSumRowLine(Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_SUM));
        for (YatzeePlayer player : players) {
            String topSum = "" + board.getTopSum(player);
            sb.append(fitInName(topSum));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTopTotalRowLine(Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOP_TOTAL));
        for (YatzeePlayer player : players) {
            String topSum = "" + board.getTopTotal(player);
            sb.append(fitInName(topSum));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getBottomSumRowLine(Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.BOTTOM_SUM));
        for (YatzeePlayer player : players) {
            String topBonus = "" + board.getBottomSum(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getTotalRowLine(Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(RowType.TOTAL));
        for (YatzeePlayer player : players) {
            String topBonus = "" + board.getTotal(player);
            sb.append(fitInName(topBonus));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getRowLine(RowType type, Board board, List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder(getRowTypeNameLine(type));
        for (YatzeePlayer player : players) {
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

    private static String getPlayerNameLine(List<YatzeePlayer> players) {
        StringBuilder sb = new StringBuilder("|");
        sb.append(fitInDescription("Player"));
        sb.append("|");
        for (Player player : players) {
            sb.append(fitInName(player.getName()));
            sb.append("|");
        }
        return sb.toString();
    }

    private static String getHorizontalSeparator(List<YatzeePlayer> players) {
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
