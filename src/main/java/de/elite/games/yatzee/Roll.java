package de.elite.games.yatzee;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;

public class Roll {

    private int currentRoll = 0;
    private Dice[][] rolls = new Dice[YatzeeGame.AMOUNT_ROLLS][YatzeeGame.AMOUNT_DICE];
    private Keeping[] keepings = new Keeping[YatzeeGame.AMOUNT_ROLLS];


    Roll() {
        for (int rollIndex = 0; rollIndex < YatzeeGame.AMOUNT_ROLLS; rollIndex++) {
            for (int diceIndex = 0; diceIndex < YatzeeGame.AMOUNT_DICE; diceIndex++) {
                rolls[rollIndex][diceIndex] = new Dice();
            }
            keepings[rollIndex] = new Keeping();
        }
    }

    @VisibleForTesting
    Roll(Dice[] dices) {
        super();
        rolls[0] = dices;
        currentRoll = 1;

    }

    void roll() {
        for (int i = 0; i < YatzeeGame.AMOUNT_DICE; i++) {
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
}
