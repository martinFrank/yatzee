package com.github.martinfrank.yahtzee;

import com.github.martinfrank.boardgamelib.BasePlayer;
import com.github.martinfrank.yahtzee.ai.RollAnalyze;
import com.github.martinfrank.yahtzee.ai.Strategy;
import com.github.martinfrank.yahtzee.ai.StrategyAdviser;
import com.github.martinfrank.yahtzee.ai.WriteAdviser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahtzeePlayer extends BasePlayer<YahtzeeGame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(YahtzeePlayer.class);
//    private Strategy currentStrategy;


    YahtzeePlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {

        YahtzeeGame yahtzeeGame = getBoardGame();//weg damit
        StrategyAdviser strategyAdviser = new StrategyAdviser(yahtzeeGame);
        Strategy currentStrategy = strategyAdviser.getAdvise();
        while (currentStrategy.adviseToContinueRolling()) {
            currentStrategy.apply(yahtzeeGame);
            currentStrategy = strategyAdviser.getAdvise();
        }

        WriteAdviser writeAdviser = new WriteAdviser(
                yahtzeeGame.getRoll(), yahtzeeGame.getBoard(), this, new RollAnalyze(yahtzeeGame.getRoll()));
        RowType rowType = writeAdviser.getOptimalRow();

        if (!yahtzeeGame.getBoard().getRow(rowType, this).isEmpty()) {
            throw new IllegalStateException("error - writing into an already filled row");
        }

        LOGGER.debug("write roll {} into rowType {}", yahtzeeGame.getRoll(), rowType);
        yahtzeeGame.write(rowType);


        LOGGER.debug("done Ai turn");
        yahtzeeGame.endPlayersTurn();
        yahtzeeGame.startPlayersTurn();
    }

//    private void executeRoll(boolean setKeeping) {
//        YahtzeeGame yatzeeGame = getBoardGame();
//        StrategyAdviser strategyAdviser = new StrategyAdviser(yatzeeGame);
//        yatzeeGame.roll();
//        LOGGER.debug("roll: {}", yatzeeGame.getRoll());
//        currentStrategy = strategyAdviser.getAdvise();
//        LOGGER.debug("best strategy seems to be {}", currentStrategy);
//
//        if(setKeeping){
//            yatzeeGame.setKeepings(currentStrategy.getKeeping());
//        }
//
//    }

//    private Keeping getKeepsForStrategyAndRoll(Strategy strategy, RollAnalyze rollAnalyze) {
//        switch (strategy) {
//            case ROLL_FOR_IDENTICAL: return applyStrategyThreeOrFourOfAKind(rollAnalyze);
//            case ROLL_FOR_FIVE_AND_SIX: return applyStrategyRollForFiveAndSix(rollAnalyze);
//            case ROLL_FOR_SIX: return applyStrategyRollForSix(rollAnalyze);
//            case ROLL_FOR_FIVE: return applyStrategyRollForFive(rollAnalyze);
//            default: return applyStrategyReRollAll();
//        }
//    }
//
//    private Keeping applyStrategyRollForFiveAndSix(RollAnalyze rollAnalyze) {
//        return applyStrategyRollForN(rollAnalyze, 5, 6);
//    }
//
//    private Keeping applyStrategyRollForSix(RollAnalyze rollAnalyze) {
//        return applyStrategyRollForN(rollAnalyze, 5);
//    }
//
//    private Keeping applyStrategyRollForFive(RollAnalyze rollAnalyze) {
//        return applyStrategyRollForN(rollAnalyze, 5);
//    }
//
//    private Keeping applyStrategyRollForN(RollAnalyze rollAnalyze, int... eyes) {
//        return rollAnalyze.getKeepingFor(eyes);
//    }
//
//    private Keeping applyStrategyReRollAll() {
//        return new Keeping(Collections.emptySet());
//    }
//
//    private Keeping applyStrategyThreeOrFourOfAKind(RollAnalyze rollAnalyze) {
//        int eye = rollAnalyze.getHighestEyeOfIdenticals();
//        return rollAnalyze.getKeepingFor(eye);
//    }
}
