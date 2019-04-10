package de.elite.games.yatzee;

import de.elite.games.cli.CommandLineInterpreter;

public class YatzeeApp
{
    public static void main( String[] args )
    {
        YatzeeGame yatzeeGame = new YatzeeGame();
        yatzeeGame.setup(new YatzeeGameSetup());
        yatzeeGame.initGame();
        CommandLineInterpreter cli = new CommandLineInterpreter(yatzeeGame, System.in, System.out);
        cli.start();
    }

}
