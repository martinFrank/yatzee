package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.Board;
import com.github.martinfrank.yahtzee.Roll;
import com.github.martinfrank.yahtzee.YahtzeeGame;
import com.github.martinfrank.yahtzee.YahtzeePlayer;

public class StrategyAdviser {

    private final YahtzeeGame yahtzeeGame;

    public StrategyAdviser(YahtzeeGame yahtzeeGame) {
        this.yahtzeeGame = yahtzeeGame;
    }

    public Strategy getAdvise() {
        Roll roll = yahtzeeGame.getRoll();
        Board board = yahtzeeGame.getBoard();
        YahtzeePlayer currentPlayer = yahtzeeGame.getCurrentPlayer();

        StrategyFactory factory = new StrategyFactory();
        if (!roll.hasRoll()) {
            return factory.createStartRollStrategy();
        }

        if (!yahtzeeGame.canRoll()) {
            return factory.createWriteToBoardStrategy();
        }

        RollAnalyze rollAnalyze = new RollAnalyze(roll);
        BoardAnalyze boardAnalyze = new BoardAnalyze(board, currentPlayer);
        int amountOfIdenticals = rollAnalyze.getAmountOfIdenticals();
        int highestEyeOfIdenticals = rollAnalyze.getHighestEyeOfIdenticals();
        boolean has6 = rollAnalyze.hasSix();
        boolean has5 = rollAnalyze.hasFive();
        boolean is6Empty = boardAnalyze.isTopRowEmpty(6);
        boolean is5Empty = boardAnalyze.isTopRowEmpty(5);
        boolean has3InRow = rollAnalyze.hasThreeInRow();
        boolean has4InRow = rollAnalyze.hasFourInRow();
        boolean hasBottomVariables = boardAnalyze.hasBottomRowsWithVariableCounter();

        if (amountOfIdenticals == 5) {
            return factory.createWriteToBoardStrategy();
        }

        if ((amountOfIdenticals >= 3 && boardAnalyze.isTopRowEmpty(highestEyeOfIdenticals))) {
            return factory.createStrategyRollForThreeOrMoreIdentical(highestEyeOfIdenticals, rollAnalyze);
        }
        if (amountOfIdenticals >= 2 && highestEyeOfIdenticals >= 4) {
            return factory.createStrategyRollForThreeOrMoreIdentical(highestEyeOfIdenticals, rollAnalyze);
        }

        if (has4InRow) {
            return factory.createStrategyRollForMajorStreet();
        }

        if (has6 && is6Empty) {
            return factory.createStrategyRollForSix(rollAnalyze);
        }

        if (((!has6) && (has5 && is5Empty))) {
            return factory.createStrategyRollForSix(rollAnalyze);
        }

        if (hasBottomVariables) {
            if (has6) {
                return factory.createStrategyRollForSix(rollAnalyze);
            }
            if (has5) {
                return factory.createStrategyRollForFive(rollAnalyze);
            }
        }

        if (has3InRow) {
            if (has5) {
                return factory.createStrategyRollForMinorStreet(3, 4, 5);
            } else {
                return factory.createStrategyRollForMinorStreet(2, 3, 4);
            }
        }

        return factory.createStrategyReRollAll();
    }

}