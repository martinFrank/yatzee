package de.elite.games.yatzee;

import de.elite.games.cli.CommandList;
import de.elite.games.cli.CommandProvider;
import de.elite.games.yatzee.command.ShowCommand;
import de.elite.games.yatzee.command.YatzeeCommandProvider;
import de.frank.martin.games.boardgamelib.BaseBoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class YatzeeGame extends BaseBoardGame<YatzeePlayer> implements CommandProvider {

    public static final int AMOUNT_DICE = 5;
    public static final int AMOUNT_ROLLS = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(YatzeeGame.class);
    private final YatzeeCommandProvider commandProvider;
    private Board board;
    private Roll currentRoll = new Roll();

    public YatzeeGame() {
        super();
        commandProvider = new YatzeeCommandProvider(this);
    }

    @Override
    public void startPlayersTurn() {
        super.startPlayersTurn();
        currentRoll = new Roll();
        LOGGER.debug("start new turn... {} ", getCurrentPlayer().getName());
        if (board.hasRowsLeft(getCurrentPlayer())) {
            if (!getCurrentPlayer().isHuman()) {
                getCurrentPlayer().performAiTurn();
            } else {
                ShowCommand.print(this);
            }
        } else {
            LOGGER.debug("game over, no more rows to fill - start a new game instead... {} ", getCurrentPlayer().getName());
        }
    }

    @Override
    public void initGame() {
        super.initGame();
        LOGGER.debug("init Yatzee Game");
        board = new Board(getPlayers());
        startPlayersTurn();
    }

    Board getBoard() {
        return board;
    }


    @Override
    public CommandList getCommands() {
        return commandProvider.getCommands();
    }

    public boolean canRoll() {
        return currentRoll.canRoll();
    }

    public boolean canSetKeeps() {
        return currentRoll.canSetKeeps();
    }

    public boolean canWrite() {
        return currentRoll.canWrite();
    }

    public void roll() {
        currentRoll.roll();
    }

    Roll getRoll() {
        return currentRoll;
    }

    public void setKeepings(Keeping keeping) {
        currentRoll.setKeepings(keeping);
    }

    public void write(RowType rowType) {
        board.write(getCurrentPlayer(), rowType, currentRoll);
    }

    public boolean hasRowsLeft() {
        return board.hasRowsLeft(getCurrentPlayer());
    }

    public boolean canWriteInRow(RowType rowType) {
        return board.canWriteInto(rowType, getCurrentPlayer());
    }
}
