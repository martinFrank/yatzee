package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.Keeping;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.Arrays;
import java.util.HashSet;

public class StrategyFactory {


    public Strategy createStrategyRollForThreeOrMoreIdentical(int highestEyeOfIdenticals, RollAnalyze rollAnalyze) {

        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: roll, try to get as much identicals of {} as possible", +rollAnalyze.getHighestEyeOfIdenticals());
                Strategy.LOGGER.debug("based on roll: {}", rollAnalyze.getRoll());
                Strategy.roll(game, rollAnalyze.getKeepingFor(highestEyeOfIdenticals));
                Strategy.LOGGER.debug("...done with set Keeping and rolling...");
            }

            @Override
            public String toString() {
                return "roll for identicals of " + highestEyeOfIdenticals;
            }

        };
    }


    public Strategy createStartRollStrategy() {
        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: roll for the first time...");
                Strategy.roll(game);
                Strategy.LOGGER.debug("...done, rolling for the first time");
            }

            @Override
            public String toString() {
                return "roll for the first time...";
            }
        };


    }

    public Strategy createWriteToBoardStrategy() {
        return new StopStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: write to board... do NOT roll anymore");
                Strategy.LOGGER.debug("...NOT DONE YET!!!");
            }

            @Override
            public String toString() {
                return "do not roll - write it into the board!";
            }
        };
    }

    public Strategy createStrategyRollForMajorStreet() {
        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: roll, try to get as much different since we have alreadey minor straight");
                Strategy.roll(game, new Keeping(new HashSet<>(Arrays.asList(2, 3, 4, 5))));
                Strategy.LOGGER.debug("...done with set Keeping and rolling...");
            }

            @Override
            public String toString() {
                return "roll for a major street";
            }
        };
    }

    public Strategy createStrategyRollForSix(RollAnalyze rollAnalyze) {
        return createStartRollForEye(6, rollAnalyze);
    }

    public Strategy createStrategyRollForFive(RollAnalyze rollAnalyze) {
        return createStartRollForEye(5, rollAnalyze);
    }

    private Strategy createStartRollForEye(int eye, RollAnalyze rollAnalyze) {
        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Keeping keeping = rollAnalyze.getKeepingFor(eye);
                Strategy.LOGGER.debug("apply strategy: roll, try to get as much identicals of {} as possible", +eye);
                Strategy.LOGGER.debug("based on roll: {}", keeping);
                Strategy.roll(game, keeping);
                Strategy.LOGGER.debug("...done with set Keeping and rolling...");
            }

            @Override
            public String toString() {
                return "roll one certain eye: " + eye;
            }
        };
    }

    public Strategy createStrategyRollForMinorStreet(Integer... eyes) {
        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: roll, try to get as much different since we have alreadey 3/4 of minor straight");
                Strategy.roll(game, new Keeping(new HashSet<>(Arrays.asList(eyes))));
                Strategy.LOGGER.debug("...done with set Keeping and rolling...");
            }

            @Override
            public String toString() {
                return "roll for a minor street";
            }
        };
    }

    public Strategy createStrategyReRollAll() {
        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                Strategy.LOGGER.debug("apply strategy: reRoll all...");
                Strategy.roll(game);
                Strategy.LOGGER.debug("...done, reRolling all");
            }

            @Override
            public String toString() {
                return "reRoll all... ";
            }
        };
    }
}
