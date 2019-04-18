package de.elite.games.yatzee.ai;

import de.elite.games.yatzee.Dice;
import de.elite.games.yatzee.Keeping;
import de.elite.games.yatzee.Roll;
import de.elite.games.yatzee.YatzeeGame;

import java.util.HashSet;
import java.util.Set;

public class RollAnalyze {

    private final Roll roll;
    private int amountOfIdenticals;
    private int highestEyeOfIdenticals;
    private int lowestEyeOfIdenticals;
    private int highestEye;
    private boolean hasSix;

    public RollAnalyze(Roll roll) {
        this.roll = roll;
        analyzeRoll();
    }

    public int getAmountOfIdenticals() {
        return amountOfIdenticals;
    }

    public int getHighestEyeOfIdenticals() {
        return highestEyeOfIdenticals;
    }

    public int getLowestEyeOfIdenticals() {
        return lowestEyeOfIdenticals;
    }

    private void analyzeRoll() {
        for (int eye = 6; eye >= 1; eye--) {
            int eyeCount = 0;
            for (Dice dice : roll.getDice()) {
                if (dice.getEyes() == eye) {
                    eyeCount = eyeCount + 1;
                }
                if (dice.getEyes() > highestEye) {
                    highestEye = dice.getEyes();
                }
                if (dice.getEyes() == 6) {
                    hasSix = true;
                }
            }
            if (eyeCount > amountOfIdenticals) {
                amountOfIdenticals = eyeCount;
                highestEyeOfIdenticals = eye;
                lowestEyeOfIdenticals = eye;
            }
            if (eyeCount == amountOfIdenticals && highestEyeOfIdenticals != eye) {
                lowestEyeOfIdenticals = eye;
            }

        }
    }

    public Keeping getKeepingFor(int... eyes) {
        Set<Integer> indexSet = new HashSet<>();
        Dice[] dice = roll.getDice();
        for (int index = 0; index < YatzeeGame.AMOUNT_DICE; index++) {
            for (int eye : eyes) {
                if (dice[index].getEyes() == eye) {
                    indexSet.add(index + 1); //because in gui indice start with 1!!
                }
            }
        }
        return new Keeping(indexSet);
    }

    public int getHighestEye() {
        return highestEye;
    }

    public Roll getRoll() {
        return roll;
    }

    public boolean hasSix() {
        return hasSix;
    }

    public boolean hasFive() {
        return hasSix;
    }

    public boolean hasThreeInRow() {
        //234
        //345
        return diceContain(2, 3, 4) || diceContain(3, 4, 5);
    }

    public boolean hasFourInRow() {
        //2345
        return diceContain(2, 3, 4, 5);
    }

    private boolean diceContain(int... eyes) {
        for (int eye : eyes) {
            boolean hasEye = false;
            for (Dice dice : roll.getDice()) {
                if (dice.getEyes() == eye) {
                    hasEye = true;
                }
            }
            if (!hasEye) {
                return false;
            }
        }
        return true;
    }
}
