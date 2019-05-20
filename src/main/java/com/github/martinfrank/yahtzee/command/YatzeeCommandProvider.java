package com.github.martinfrank.yahtzee.command;

import com.github.martinfrank.cli.CommandList;
import com.github.martinfrank.cli.CommandProvider;
import com.github.martinfrank.cli.DefaultCommandList;
import com.github.martinfrank.yahtzee.YahtzeeGame;

public class YatzeeCommandProvider implements CommandProvider {

    private final YahtzeeGame yahtzeeGame;
    private final ShowCommand showCommand;
    private final RollDiceCommand rollDiceCommand;
    private final SetKeepsCommand setKeepsCommand;
    private final WriteCommand writeCommand;
    private final NewGameCommand newGameCommand;

    public YatzeeCommandProvider(YahtzeeGame yahtzeeGame) {
        super();
        this.yahtzeeGame = yahtzeeGame;
        showCommand = new ShowCommand(yahtzeeGame);
        rollDiceCommand = new RollDiceCommand(yahtzeeGame);
        setKeepsCommand = new SetKeepsCommand(yahtzeeGame);
        writeCommand = new WriteCommand(yahtzeeGame);
        newGameCommand = new NewGameCommand(yahtzeeGame);
    }

    @Override
    public CommandList getCommands() {
        final DefaultCommandList commandMapping = new DefaultCommandList();
        commandMapping.add(showCommand);
        if (yahtzeeGame.canRoll() && yahtzeeGame.hasRowsLeft()) {
            commandMapping.add(rollDiceCommand);
        }
        if (yahtzeeGame.canSetKeeps()) {
            commandMapping.add(setKeepsCommand);
        }
        if (yahtzeeGame.canWrite()) {
            commandMapping.add(writeCommand);
        }
        commandMapping.add(newGameCommand);
        return commandMapping;
    }

}
