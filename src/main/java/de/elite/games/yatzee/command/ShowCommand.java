package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.BoardPrinter;
import de.elite.games.yatzee.RollPrinter;
import de.elite.games.yatzee.YatzeeGame;

import java.util.List;

public class ShowCommand extends Command<YatzeeGame> {

    ShowCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "show");
    }

    public static void print(YatzeeGame game) {
        BoardPrinter.print(System.out, game);
        RollPrinter.print(System.out, game);
    }

    @Override
    public Response execute(List<String> list) {
        print(getApplication());
        return Response.success();
    }
}
