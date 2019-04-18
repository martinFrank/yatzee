package de.elite.games.yatzee;

import de.elite.games.yatzee.ai.*;
import de.frank.martin.games.boardgamelib.BasePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class YatzeePlayer extends BasePlayer<YatzeeGame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(YatzeePlayer.class);

    YatzeePlayer(String name, int color, boolean isHuman) {
        super(name, color, isHuman);
    }

    @Override
    public void performAiTurn() {
        YatzeeGame yatzeeGame = getBoardGame();

        //first turn - roll and find best keepings
        yatzeeGame.roll();
        Roll firstRoll = yatzeeGame.getRoll();
        LOGGER.debug("first roll {}", firstRoll);

        RollAnalyze firstRollAnalyze = new RollAnalyze(firstRoll);
        BoardAnalyze boardAnalyze = new BoardAnalyze(yatzeeGame.getBoard(), this);

        Strategy firstStrategy = StrategyAdviser.getAdvise(firstRollAnalyze, boardAnalyze);
        LOGGER.debug("following the 1st. strategy {}", firstStrategy);
        getKeepsForStrategyAndRoll(firstStrategy, yatzeeGame, firstRollAnalyze);

        //second turn - roll again and find best keeping
        yatzeeGame.roll();
        Roll secondRoll = yatzeeGame.getRoll();
        LOGGER.debug("second roll {}", secondRoll);

        RollAnalyze secondRollAnalyze = new RollAnalyze(secondRoll);

        Strategy secondStrategy = StrategyAdviser.getAdvise(secondRollAnalyze, boardAnalyze);
        LOGGER.debug("following the 2nd. strategy {}", secondStrategy);
        Keeping keeping = getKeepsForStrategyAndRoll(secondStrategy, yatzeeGame, secondRollAnalyze);
        LOGGER.debug("keeping: {}", keeping);
        yatzeeGame.setKeepings(keeping);


        //third turn - roll for the last time & write result
        yatzeeGame.roll();
        Roll thirdRoll = yatzeeGame.getRoll();
        LOGGER.debug("third roll {}", thirdRoll);
        RollAnalyze thirdRollAnalyze = new RollAnalyze(thirdRoll);


        WriteAdviser writeAdviser = new WriteAdviser(
                thirdRoll, yatzeeGame.getBoard(), this, thirdRollAnalyze);
        RowType rowType = writeAdviser.getOptimalRow();

        if (!yatzeeGame.getBoard().getRow(rowType, this).isEmpty()) {
            throw new IllegalStateException("error - writing into an already filled row");
        }

        LOGGER.debug("write roll {} into rowType {}", thirdRoll, rowType);
        yatzeeGame.write(rowType);


        LOGGER.debug("done Ai turn");
        yatzeeGame.endPlayersTurn();
        yatzeeGame.startPlayersTurn();
    }

    private Keeping getKeepsForStrategyAndRoll(Strategy strategy, YatzeeGame yatzeeGame,
                                               RollAnalyze rollAnalyze) {
        switch (strategy) {
            case ROLL_FOR_IDENTICAL: return applyStrategyThreeOrFourOfAKind(yatzeeGame, rollAnalyze);
            case ROLL_FOR_FIVE_AND_SIX: return applyStrategyRollForFiveAndSix(yatzeeGame, rollAnalyze);
            case ROLL_FOR_SIX: return applyStrategyRollForSix(yatzeeGame, rollAnalyze);
            case ROLL_FOR_FIVE: return applyStrategyRollForFive(yatzeeGame, rollAnalyze);
            default: return applyStrategyReRollAll();
        }
    }

    private Keeping applyStrategyRollForFiveAndSix(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        return applyStrategyRollForN(yatzeeGame, rollAnalyze, 5, 6);
    }

    private Keeping applyStrategyRollForSix(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        return applyStrategyRollForN(yatzeeGame, rollAnalyze, 5);
    }

    private Keeping applyStrategyRollForFive(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        return applyStrategyRollForN(yatzeeGame, rollAnalyze, 5);
    }

    private Keeping applyStrategyRollForN(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze, int... eyes) {
        return rollAnalyze.getKeepingFor(eyes);
    }

    private Keeping applyStrategyReRollAll() {
        return new Keeping(Collections.emptySet());
    }

    private Keeping applyStrategyThreeOrFourOfAKind(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        int eye = rollAnalyze.getHighestEyeOfIdenticals();
        return rollAnalyze.getKeepingFor(eye);
    }
}
