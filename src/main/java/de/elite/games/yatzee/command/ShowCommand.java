package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.BoardPrinter;
import de.elite.games.yatzee.HintPrinter;
import de.elite.games.yatzee.RollPrinter;
import de.elite.games.yatzee.YatzeeGame;

import java.io.PrintStream;
import java.util.List;

public class ShowCommand extends Command<YatzeeGame> {

    ShowCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "show");
    }

    public static void print(YatzeeGame game) {
        PrintStream ps = new PrintStream(System.out);
        BoardPrinter.print(ps, game);
        HintPrinter.print(ps, game);
        RollPrinter.print(ps, game);
    }

    @Override
    public Response execute(List<String> list) {
        print(getApplication());
        return Response.success();
    }
}
