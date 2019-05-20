package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.Keeping;
import com.github.martinfrank.yahtzee.YahtzeeGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

public class StrategyFactory {


    public Strategy createStrategyRollForThreeOrMoreIdentical(int highestEyeOfIdenticals, RollAnalyze rollAnalyze) {

        return new ContinueStrategy() {

            @Override
            public void apply(YahtzeeGame game) {
                LOGGER.debug("apply strategy: roll, try to get as much identicals of {} as possible", +rollAnalyze.getHighestEyeOfIdenticals());
                LOGGER.debug("based on roll: {}", rollAnalyze.getRoll());
                roll(game, rollAnalyze.getKeepingFor(highestEyeOfIdenticals));
                LOGGER.debug("...done with set Keeping and rolling...");
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
                LOGGER.debug("apply strategy: roll for the first time...");
                roll(game);
                LOGGER.debug("...done, rolling for the first time");
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
                LOGGER.debug("apply strategy: write to board... do NOT roll anymore");
                LOGGER.debug("...NOT DONE YET!!!");
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
                LOGGER.debug("apply strategy: roll, try to get as much different since we have alreadey minor straight");
                roll(game, new Keeping(new HashSet<>(Arrays.asList(2, 3, 4, 5))));
                LOGGER.debug("...done with set Keeping and rolling...");
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
                LOGGER.debug("apply strategy: roll, try to get as much identicals of {} as possible", +eye);
                LOGGER.debug("based on roll: {}", keeping);
                roll(game, keeping);
                LOGGER.debug("...done with set Keeping and rolling...");
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
                LOGGER.debug("apply strategy: roll, try to get as much different since we have alreadey 3/4 of minor straight");
                roll(game, new Keeping(new HashSet<>(Arrays.asList(eyes))));
                LOGGER.debug("...done with set Keeping and rolling...");
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
                LOGGER.debug("apply strategy: reRoll all...");
                roll(game);
                LOGGER.debug("...done, reRolling all");
            }

            @Override
            public String toString() {
                return "reRoll all... ";
            }
        };
    }


    private abstract class TemplateStrategy implements Strategy {
        private final boolean adviseToContinue;
        Logger LOGGER = LoggerFactory.getLogger(Strategy.class);

        private TemplateStrategy(final boolean adviseToContinue) {
            this.adviseToContinue = adviseToContinue;
        }

        @Override
        public boolean adviseToContinueRolling() {
            return adviseToContinue;
        }

        void roll(YahtzeeGame yahtzeeGame, Keeping keeping) {
            LOGGER.debug("set keeping: {}", keeping);
            yahtzeeGame.setKeepings(keeping);
            yahtzeeGame.roll();
        }

        void roll(YahtzeeGame yahtzeeGame) {
            yahtzeeGame.roll();
        }
    }

    private abstract class StopStrategy extends TemplateStrategy {

        private StopStrategy() {
            super(false);
        }

    }

    private abstract class ContinueStrategy extends TemplateStrategy {

        private ContinueStrategy() {
            super(true);
        }

    }
}
