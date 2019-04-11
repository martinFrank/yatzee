package de.elite.games.yatzee.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StrategyAdviser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyAdviser.class);

    public static Strategy getAdvise(int round, RollAnalyze rollAnalyze, BoardAnalyze boardAnalyze) {

        int amountOfIdenticals = rollAnalyze.getAmountOfIdenticals();
        int highestEyeOfIdenticals = rollAnalyze.getHighestEyeOfIdenticals();
        int lowestEyeOfIdenticals = rollAnalyze.getLowestEyeOfIdenticals();
        int highestEye = rollAnalyze.getHighestEye();

        LOGGER.debug("roll: {}", rollAnalyze.getRoll());
        LOGGER.debug("most identicals: {} x of {} ", amountOfIdenticals, highestEyeOfIdenticals);
        LOGGER.debug("most identicals: {} x of {} ", amountOfIdenticals, lowestEyeOfIdenticals);
        LOGGER.debug("highest any    : {} ", highestEye);
        boolean has6 = rollAnalyze.hasSix();
        boolean has5 = rollAnalyze.hasFive();
        boolean is6Empty = boardAnalyze.isTopRowEmpty(6);
        boolean is5Empty = boardAnalyze.isTopRowEmpty(5);
        boolean has3InRow = rollAnalyze.hasThreeInRow();
        boolean has4InRow = rollAnalyze.hasFourInRow();
        boolean hasBottomVariables = boardAnalyze.hasBottomRowsWithVariableCounter();

        //FIXME missing strategy for writeEarly

        if ((amountOfIdenticals >= 3 && boardAnalyze.isTopRowEmpty(highestEyeOfIdenticals))) {
            return Strategy.ROLL_FOR_IDENTICAL;
        }

        if (amountOfIdenticals >= 2 && highestEyeOfIdenticals >= 4) {
            return Strategy.ROLL_FOR_IDENTICAL;
        }

        if (has4InRow) {
            return Strategy.ROLL_FOR_MAJOR_STRAIGHT;
        }

        if (((has6 && is6Empty) && (has5 && is5Empty))) {
            return Strategy.ROLL_FOR_FIVE_AND_SIX;
        }

        if (((has6 && is6Empty) && (!has5))) {
            return Strategy.ROLL_FOR_SIX;
        }

        if (((!has6) && (has5 && is5Empty))) {
            return Strategy.ROLL_FOR_FIVE;
        }

        if (hasBottomVariables) {
            return Strategy.ROLL_FOR_FIVE;
        }

        if (has3InRow) {
            return Strategy.ROLL_FOR_MINOR_STRAIGHT;
        }


        return Strategy.REROLL_ALL;
    }

}