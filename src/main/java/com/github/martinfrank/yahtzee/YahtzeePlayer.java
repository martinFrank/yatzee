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

}
