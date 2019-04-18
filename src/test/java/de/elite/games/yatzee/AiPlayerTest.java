package de.elite.games.yatzee;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class AiPlayerTest {

    @Test
    public void testStrategy() {
        //FIXME
        Assert.assertTrue(true);
        YatzeeGame yatzeeGame = new YatzeeGame();
        yatzeeGame.setup(new TestGameSetup());
        yatzeeGame.initGame();
        yatzeeGame.getCommands().findCommand("show").ifPresent(c -> c.execute(Collections.emptyList()));
        Assert.assertFalse(yatzeeGame.hasRowsLeft());
    }
}
