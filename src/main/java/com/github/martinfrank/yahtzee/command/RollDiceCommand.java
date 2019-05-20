package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.List;

public class RollDiceCommand extends Command<YahtzeeGame> {

    RollDiceCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "roll");
    }

    @Override
    public Response execute(List<String> list) {
        getApplication().roll();
        ShowCommand.print(getApplication());
        return Response.success();
    }
}
