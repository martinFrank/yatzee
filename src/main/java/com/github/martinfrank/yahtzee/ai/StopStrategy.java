package com.github.martinfrank.yahtzee.ai;

public abstract class StopStrategy implements Strategy {

    @Override
    public boolean adviseToContinueRolling() {
        return false;
    }

}
