package de.elite.games.yatzee.command;

import de.elite.games.cli.Command;
import de.elite.games.cli.Response;
import de.elite.games.yatzee.RowType;
import de.elite.games.yatzee.YatzeeGame;

import java.util.List;

public class WriteCommand extends Command<YatzeeGame> {

    WriteCommand(YatzeeGame yatzeeGame) {
        super(yatzeeGame, "write");
    }

    @Override
    public Response execute(List<String> list) {
        if (list.size() == 1) {
            try {
                RowType rowType = null;
                for (RowType candidate : RowType.values()) {
                    if (candidate.getName().equalsIgnoreCase(list.get(0))) {
                        rowType = candidate;
                        break;
                    }
                }
                if (rowType == null) {
                    throw new IllegalArgumentException("unknown row type");
                }
                getApplication().write(rowType);
                getApplication().endPlayersTurn();
                getApplication().startPlayersTurn();
                return Response.success();
            } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                return Response.fail("not clear into which row should be written: " + list);
            }
        }
        return Response.fail("only one row may be named: " + list);
    }
}
