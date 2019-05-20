package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.List;

public class NewGameCommand extends Command<YahtzeeGame> {

    public NewGameCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "newGame");
    }

    @Override
    public Response execute(List<String> list) {
        getApplication().initGame();
        return Response.success();
    }
}
