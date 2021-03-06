package com.github.martinfrank.yahtzee;


import java.util.Random;

public class Dice {

    private final Random random;
    private int eyes;
    private boolean isSet;

    Dice(Random random) {
        this.random = random;
    }

    Dice() {
        this(new Random());
    }

    //@VisibleForTesting
    Dice(int eyes) {
        this();
        this.eyes = eyes;
        isSet = true;
    }

    void roll() {
        eyes = random.nextInt(6) + 1;
        isSet = true;
    }

    public int getEyes() {
        if (isSet) {
            return eyes;
        }
        throw new IllegalStateException("dice has not been rolled yet");
    }

    public String toString() {
        return isSet ? "" + eyes : " ";
    }
}
