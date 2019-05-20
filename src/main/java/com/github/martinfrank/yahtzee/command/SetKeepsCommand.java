package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.Keeping;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetKeepsCommand extends Command<YahtzeeGame> {

    SetKeepsCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "keep");
    }

    @Override
    public Response execute(List<String> list) {
        try {
            Set<Integer> keepIndices = new HashSet<>();
            for (String str : list) {
                for (int i = 0; i < str.length(); i++) {
                    String c = "" + str.charAt(i);
                    keepIndices.add(Integer.parseInt(c));
                }

            }
            if (Keeping.isValidInput(keepIndices)) {
                Keeping keeping = new Keeping(keepIndices);
                getApplication().setKeepings(keeping);
                ShowCommand.print(getApplication());
                return Response.success();
            } else {
                return Response.fail("could not understand keepings: " + list);
            }
        } catch (NumberFormatException e) {
            return Response.fail("could not understand keepings: " + list);
        }
    }
}
