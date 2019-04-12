package de.elite.games.yatzee;

import de.elite.games.yatzee.ai.BoardAnalyze;
import de.elite.games.yatzee.ai.RollAnalyze;
import de.elite.games.yatzee.ai.WriteAdviser;

import java.io.PrintStream;

public class RollPrinter {

    private static final String SEP = "+-----------+---+---+---+---+---+";
    private static final String HERE = "<-- you are here";
    private static final String KEEP = "<-- you can keep these";

    public static void print(PrintStream out, YatzeeGame yatzeeGame) {
        if (yatzeeGame.hasRowsLeft()) {
            out.println(SEP);
            out.println("|  dice #   | 1 | 2 | 3 | 4 | 5 |");
            out.println(SEP);
            out.println(getDiceLine(1, yatzeeGame.getRoll()));
            out.println(SEP);
            out.println(getKeepLine(1, yatzeeGame.getRoll()));
            out.println(SEP);
            out.println(getDiceLine(2, yatzeeGame.getRoll()));
            out.println(SEP);
            out.println(getKeepLine(2, yatzeeGame.getRoll()));
            out.println(SEP);
            out.println(getDiceLine(3, yatzeeGame.getRoll()));
            out.println(SEP);
            out.println();


            if (yatzeeGame.getRoll().canWrite()) {
                WriteAdviser writeAdviser = new WriteAdviser(
                        yatzeeGame.getRoll(),
                        yatzeeGame.getBoard(),
                        yatzeeGame.getCurrentPlayer(),
                        new BoardAnalyze(yatzeeGame.getBoard(), yatzeeGame.getCurrentPlayer()),
                        new RollAnalyze(yatzeeGame.getRoll()));
                RowType rowType = writeAdviser.getOptimalRow();
                int value = RollCalculator.getValue(rowType, yatzeeGame.getRoll());

                out.println("thoughts from the AI... \'write into \"" + rowType.getName() + "\" for " + value + " points...\'");
            }

            out.println();
        }
    }

    private static String getKeepLine(int currentIndex, Roll roll) {
        StringBuilder sb = new StringBuilder("|   keep    |");
        Keeping keeping = roll.getKeeping(currentIndex - 1);
        for (boolean b : keeping.getKeepings()) {
            sb.append(" " + (b ? "k" : " ") + " |");
        }
        if (currentIndex == roll.getCurrentIndex()) {
            sb.append(KEEP);
        }
        return sb.toString();
    }

    private static String getDiceLine(int currentIndex, Roll roll) {
        StringBuilder sb = new StringBuilder(createIndex(currentIndex));
        for (Dice dice : roll.getDice(currentIndex - 1)) {
            sb.append(" " + dice.toString() + " |");
        }
        if (currentIndex == roll.getCurrentIndex() + 1) {
            sb.append(HERE);
        }
        return sb.toString();
    }

    private static String createIndex(int currentIndex) {
        return "|  roll #" + currentIndex + "  |";
    }


}
