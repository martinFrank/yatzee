package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.HelpPrinter;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.List;

public class HelpYatzeeCommand extends Command<YahtzeeGame> {

    public HelpYatzeeCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "helpYatzee");
    }

    @Override
    public Response execute(List<String> list) {
        HelpPrinter.print(System.out);
        return Response.success();
    }
}
