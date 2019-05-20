package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.Command;
import com.github.martinfrank.cli.Response;
import com.github.martinfrank.yahtzee.RowType;
import com.github.martinfrank.yahtzee.YahtzeeGame;

import java.util.List;

public class WriteCommand extends Command<YahtzeeGame> {

    WriteCommand(YahtzeeGame yahtzeeGame) {
        super(yahtzeeGame, "write");
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
                boolean canWriteInRow = getApplication().canWriteInRow(rowType);
                if (!canWriteInRow) {
                    return Response.fail("you cannot write into that row(" + rowType.getName() + "), it's already set....");
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
