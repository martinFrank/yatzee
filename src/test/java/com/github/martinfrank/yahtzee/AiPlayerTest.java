package com.github.martinfrank.yahtzee;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class AiPlayerTest {

    @Test
    public void testStrategy() {
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(true);
            YahtzeeGame yahtzeeGame = new YahtzeeGame();
            yahtzeeGame.setup(new TestGameSetup());
            yahtzeeGame.initGame();
            yahtzeeGame.getCommands().findCommand("show").ifPresent(c -> c.execute(Collections.emptyList()));
            Assert.assertFalse(yahtzeeGame.hasRowsLeft());
        }
    }
}
