package de.elite.games.yatzee;

import de.elite.games.yatzee.ai.*;

import java.io.PrintStream;

public class HintPrinter {

    public static void print(PrintStream out, YatzeeGame yatzeeGame) {
        if (yatzeeGame.getRoll().canWrite()) {

            Roll roll = yatzeeGame.getRoll();
            Board board = yatzeeGame.getBoard();
            YatzeePlayer player = yatzeeGame.getCurrentPlayer();
            RollAnalyze rollAnalyze = new RollAnalyze(roll);
            BoardAnalyze boardAnalyze = new BoardAnalyze(board, player);
            Strategy strategy = StrategyAdviser.getAdvise(rollAnalyze, boardAnalyze);
            WriteAdviser writeAdviser = new WriteAdviser(roll, board, player, boardAnalyze, rollAnalyze);
            RowType rowType = writeAdviser.getOptimalRow();
            int value = RollCalculator.getValue(rowType, yatzeeGame.getRoll());

            out.println("thoughts from the AI... \'try to set keeping for " + strategy.name());
            out.println("thoughts from the AI... \'write into \"" + rowType.getName() + "\" for " + value + " points...\'");
            out.println();
        }
    }
}
