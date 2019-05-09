package de.elite.games.yatzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import de.elite.games.yatzee.YatzeeGame;

import java.util.List;

public class RollDiceCommand extends Command<YatzeeGame> {

    RollDiceCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "roll");
    }

    @Override
    public Response execute(List<String> list) {
        getApplication().roll();
        ShowCommand.print(getApplication());
        return Response.success();
    }
}
