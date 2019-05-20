package com.github.martinfrank.yahtzee;

import java.util.HashSet;
import java.util.Set;

public class Keeping {

    private boolean[] keepings;

    public Keeping() {
        this(new HashSet<>());
    }

    public Keeping(Set<Integer> keepIndices) {
        keepings = new boolean[YahtzeeGame.AMOUNT_DICE];
        for (int index : keepIndices) {
            keepings[index - 1] = true;
        }
    }

    public static boolean isValidInput(Set<Integer> keepIndices) {
        if (keepIndices == null) {
            return false;
        }
        if (keepIndices.size() > 5) {
            return false;
        }
        for (int index : keepIndices) {
            if (index < 1 || index > 5) {
                return false;
            }
        }
        return true;
    }

    void apply(Dice[] current, Dice[] previous) {
        for (int i = 0; i < YahtzeeGame.AMOUNT_DICE; i++) {
            if (keepings[i]) {
                current[i] = previous[i];
            }
        }
    }

    boolean[] getKeepings() {
        return keepings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < YahtzeeGame.AMOUNT_DICE; i++) {
            sb.append(keepings[i] ? "k" : " ");
            if (i < YahtzeeGame.AMOUNT_DICE - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
