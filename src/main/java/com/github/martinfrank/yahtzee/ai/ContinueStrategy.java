package com.github.martinfrank.yahtzee.ai;

public abstract class ContinueStrategy implements Strategy {

    @Override
    public boolean adviseToContinueRolling() {
        return true;
    }
}
