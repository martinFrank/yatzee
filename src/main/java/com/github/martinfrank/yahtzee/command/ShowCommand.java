package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.BoardPrinter;
import com.github.martinfrank.yahtzee.HintPrinter;
import com.github.martinfrank.yahtzee.RollPrinter;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.io.PrintStream;
import java.util.List;

public class ShowCommand extends Command<YahtzeeGame> {

    ShowCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "show");
    }

    public static void print(YahtzeeGame game) {
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
