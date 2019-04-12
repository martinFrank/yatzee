package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.Keeping;
import de.elite.games.yatzee.YatzeeGame;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetKeepsCommand extends Command<YatzeeGame> {

    SetKeepsCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "keep");
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
