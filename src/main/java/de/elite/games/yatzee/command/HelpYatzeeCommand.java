package de.elite.games.yatzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
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
