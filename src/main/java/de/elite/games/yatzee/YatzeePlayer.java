package de.elite.games.yatzee;

import de.elite.games.yatzee.ai.*;
import de.frank.martin.games.boardgamelib.BasePlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        Strategy firstStrategy = StrategyAdviser.getAdvise(1, firstRollAnalyze, boardAnalyze);
        LOGGER.debug("following the 1st. strategy {}", firstStrategy);
        applyStrategyAndRoll(firstStrategy, yatzeeGame, firstRollAnalyze);

        //second turn - roll again and find best keeping
        yatzeeGame.roll();
        Roll secondRoll = yatzeeGame.getRoll();
        LOGGER.debug("second roll {}", secondRoll);

        RollAnalyze secondRollAnalyze = new RollAnalyze(secondRoll);

        Strategy secondStrategy = StrategyAdviser.getAdvise(2, secondRollAnalyze, boardAnalyze);
        LOGGER.debug("following the 2nd. strategy {}", secondStrategy);
        applyStrategyAndRoll(secondStrategy, yatzeeGame, secondRollAnalyze);


        //third turn - roll for the last time & write result
        yatzeeGame.roll();
        Roll thirdRoll = yatzeeGame.getRoll();
        LOGGER.debug("third roll {}", thirdRoll);
        RollAnalyze thirdRollAnalyze = new RollAnalyze(thirdRoll);


        WriteAdviser writeAdviser = new WriteAdviser(
                thirdRoll, yatzeeGame.getBoard(), this, boardAnalyze, thirdRollAnalyze);
        RowType rowType = writeAdviser.getOptimalRow();

        if (!yatzeeGame.getBoard().getRow(rowType, this).isEmpty()) {
            throw new IllegalStateException("error - writing into an already filled row");
        }

        LOGGER.debug("write roll {} into rowType {}", thirdRoll, rowType);
        yatzeeGame.write(rowType);


        System.out.println("done Ai turn");
        yatzeeGame.endPlayersTurn();
        yatzeeGame.startPlayersTurn();
    }

    private void applyStrategyAndRoll(Strategy strategy, YatzeeGame yatzeeGame,
                                      RollAnalyze rollAnalyze) {
        switch (strategy) {
            case ROLL_FOR_IDENTICAL: {
                applyStrategyThreeOrFourOfAKind(yatzeeGame, rollAnalyze);
                break;
            }
            case ROLL_FOR_FIVE_AND_SIX: {
                applyStrategyRollForFiveAndSix(yatzeeGame, rollAnalyze);
                break;
            }
            case ROLL_FOR_SIX: {
                applyStrategyRollForSix(yatzeeGame, rollAnalyze);
                break;
            }
            case ROLL_FOR_FIVE: {
                applyStrategyRollForFive(yatzeeGame, rollAnalyze);
                break;
            }
            default: applyStrategyReRollAll();
        }
    }

    private void applyStrategyRollForFiveAndSix(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        applyStrategyRollForN(yatzeeGame, rollAnalyze, 5, 6);
    }

    private void applyStrategyRollForSix(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        applyStrategyRollForN(yatzeeGame, rollAnalyze, 5);
    }

    private void applyStrategyRollForFive(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        applyStrategyRollForN(yatzeeGame, rollAnalyze, 5);
    }

    private void applyStrategyRollForN(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze, int... eyes) {
        Keeping keeping = rollAnalyze.getKeepingFor(eyes);
        yatzeeGame.setKeepings(keeping);
        LOGGER.debug("keeping: {}", keeping);
    }

    private void applyStrategyReRollAll() {
        //nothing left to do
    }

    private void applyStrategyThreeOrFourOfAKind(YatzeeGame yatzeeGame, RollAnalyze rollAnalyze) {
        int eye = rollAnalyze.getHighestEyeOfIdenticals();
        Keeping keeping = rollAnalyze.getKeepingFor(eye);
        yatzeeGame.setKeepings(keeping);
        LOGGER.debug("keeping: {}", keeping);
    }
}
