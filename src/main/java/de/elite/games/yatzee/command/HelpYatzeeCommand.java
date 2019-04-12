package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.HelpPrinter;
import de.elite.games.yatzee.YatzeeGame;

import java.util.List;

public class HelpYatzeeCommand extends Command<YatzeeGame> {

    public HelpYatzeeCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "helpYatzee");
    }

    @Override
    public Response execute(List<String> list) {
        HelpPrinter.print(System.out);
        return Response.success();
    }
}
