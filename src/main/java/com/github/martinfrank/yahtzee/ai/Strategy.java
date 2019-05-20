package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.Keeping;
import com.github.martinfrank.yahtzee.YahtzeeGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Strategy {

    Logger LOGGER = LoggerFactory.getLogger(Strategy.class);

    static void roll(YahtzeeGame yahtzeeGame, Keeping keeping) {
        LOGGER.debug("set keeping: {}", keeping);
        yahtzeeGame.setKeepings(keeping);
        yahtzeeGame.roll();
    }

    static void roll(YahtzeeGame yahtzeeGame) {
        yahtzeeGame.roll();
    }

    boolean adviseToContinueRolling();

    void apply(YahtzeeGame game);
}
