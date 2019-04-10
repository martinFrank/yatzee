package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.YatzeeGame;

import java.util.List;

public class NewGameCommand extends Command<YatzeeGame> {

    public NewGameCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "newGame");
    }

    @Override
    public Response execute(List<String> list) {
        getApplication().initGame();
        return Response.success();
    }
}
