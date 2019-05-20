package com.github.martinfrank.yahtzee.ai;

import com.github.martinfrank.yahtzee.YahtzeeGame;

public interface Strategy {



    boolean adviseToContinueRolling();

    void apply(YahtzeeGame game);
}
