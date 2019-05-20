package com.github.martinfrank.yahtzee;

import com.github.martinfrank.yahtzee.ai.RollAnalyze;
import com.github.martinfrank.yahtzee.ai.Strategy;
import com.github.martinfrank.yahtzee.ai.StrategyAdviser;
import com.github.martinfrank.yahtzee.ai.WriteAdviser;

import java.io.PrintStream;

public class HintPrinter {

    private HintPrinter() {

    }

    public static void print(PrintStream out, YahtzeeGame yahtzeeGame) {
        if (yahtzeeGame.getRoll().canWrite()) {

            Roll roll = yahtzeeGame.getRoll();
            Board board = yahtzeeGame.getBoard();
            YahtzeePlayer player = yahtzeeGame.getCurrentPlayer();
            RollAnalyze rollAnalyze = new RollAnalyze(roll);
            StrategyAdviser strategyAdviser = new StrategyAdviser(yahtzeeGame);
            Strategy strategy = strategyAdviser.getAdvise();
            WriteAdviser writeAdviser = new WriteAdviser(roll, board, player, rollAnalyze);
            RowType rowType = writeAdviser.getOptimalRow();
            int value = RollCalculator.getValue(rowType, yahtzeeGame.getRoll());

            out.println("thoughts from the AI (@Strategy): \'try to follow strategy: " + strategy + "\'");
            out.println("thoughts from the AI (@Board): \'write into \"" + rowType.getName() + "\" for " + value + " points...\'");
        }
    }
}
