package com.github.martinfrank.yahtzee;

import java.util.Arrays;

public class Roll {

    private int currentRoll = 0;
    private Dice[][] rolls = new Dice[YahtzeeGame.AMOUNT_ROLLS][YahtzeeGame.AMOUNT_DICE];
    private Keeping[] keepings = new Keeping[YahtzeeGame.AMOUNT_ROLLS];


    Roll() {
        for (int rollIndex = 0; rollIndex < YahtzeeGame.AMOUNT_ROLLS; rollIndex++) {
            for (int diceIndex = 0; diceIndex < YahtzeeGame.AMOUNT_DICE; diceIndex++) {
                rolls[rollIndex][diceIndex] = new Dice();
            }
            keepings[rollIndex] = new Keeping();
        }
    }

    //@VisibleForTesting
    Roll(Dice[] dices) {
        super();
        rolls[0] = dices;
        currentRoll = 1;

    }

    void roll() {
        for (int i = 0; i < YahtzeeGame.AMOUNT_DICE; i++) {
            rolls[currentRoll][i].roll();
        }
        applyKeeping();
        currentRoll = currentRoll + 1;
    }

    void setKeepings(Keeping keeping) {
        keepings[currentRoll - 1] = keeping;
    }


    private void applyKeeping() {
        if (currentRoll > 0) {
            keepings[currentRoll - 1].apply(rolls[currentRoll], rolls[currentRoll - 1]);
        }
    }

    boolean canRoll() {
        return currentRoll <= 2;
    }

    boolean canSetKeeps() {
        return currentRoll > 0 && currentRoll <= 2;
    }

    boolean canWrite() {
        return currentRoll > 0;
    }

    Dice[] getDice(int index) {
        return rolls[index];
    }

    public Dice[] getDice() {
        return rolls[currentRoll - 1];
    }

    @Override
    public String toString() {
        return Arrays.toString(getDice(currentRoll - 1));
    }

    int getCurrentIndex() {
        return currentRoll;
    }

    Keeping getKeeping(int index) {
        return keepings[index];
    }

    public boolean hasRoll() {
        return currentRoll > 0;
    }
}
