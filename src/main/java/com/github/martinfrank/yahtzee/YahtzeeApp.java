package com.github.martinfrank.yahtzee;

import com.github.martinfrank.cli.CommandLineInterpreter;

public class YahtzeeApp
{
    public static void main( String[] args )
    {
        YahtzeeGame yahtzeeGame = new YahtzeeGame();
        yahtzeeGame.setup(new YahtzeeGameSetup());
        yahtzeeGame.initGame();
        CommandLineInterpreter cli = new CommandLineInterpreter(yahtzeeGame, System.in, System.out);
        cli.start();
    }

}
