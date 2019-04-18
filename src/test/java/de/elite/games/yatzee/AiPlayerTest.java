package de.elite.games.yatzee;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class AiPlayerTest {

    @Test
    public void testStrategy() {
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(true);
            YatzeeGame yatzeeGame = new YatzeeGame();
            yatzeeGame.setup(new TestGameSetup());
            yatzeeGame.initGame();
            yatzeeGame.getCommands().findCommand("show").ifPresent(c -> c.execute(Collections.emptyList()));
            Assert.assertFalse(yatzeeGame.hasRowsLeft());
        }
    }
}
