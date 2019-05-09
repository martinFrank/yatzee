package de.elite.games.yatzee.command;

import com.github.martinfrank.cli.CommandList;
import com.github.martinfrank.cli.CommandProvider;
import com.github.martinfrank.cli.DefaultCommandList;
import de.elite.games.yatzee.YatzeeGame;

public class YatzeeCommandProvider implements CommandProvider {

    private final YatzeeGame yatzeeGame;
    private final ShowCommand showCommand;
    private final RollDiceCommand rollDiceCommand;
    private final SetKeepsCommand setKeepsCommand;
    private final WriteCommand writeCommand;
    private final NewGameCommand newGameCommand;

    public YatzeeCommandProvider(YatzeeGame yatzeeGame) {
        super();
        this.yatzeeGame = yatzeeGame;
        showCommand = new ShowCommand(yatzeeGame);
        rollDiceCommand = new RollDiceCommand(yatzeeGame);
        setKeepsCommand = new SetKeepsCommand(yatzeeGame);
        writeCommand = new WriteCommand(yatzeeGame);
        newGameCommand = new NewGameCommand(yatzeeGame);
    }

    @Override
    public CommandList getCommands() {
        final DefaultCommandList commandMapping = new DefaultCommandList();
        commandMapping.add(showCommand);
        if (yatzeeGame.canRoll() && yatzeeGame.hasRowsLeft()) {
            commandMapping.add(rollDiceCommand);
        }
        if (yatzeeGame.canSetKeeps()) {
            commandMapping.add(setKeepsCommand);
        }
        if (yatzeeGame.canWrite()) {
            commandMapping.add(writeCommand);
        }
        commandMapping.add(newGameCommand);
        return commandMapping;
    }

}
