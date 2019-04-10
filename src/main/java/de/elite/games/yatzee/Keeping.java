package de.elite.games.yatzee;

import java.util.HashSet;
import java.util.Set;

class Keeping {

    private boolean[] keepings;

    Keeping() {
        this(new HashSet<>());
    }

    Keeping(Set<Integer> keepIndices) {
        keepings = new boolean[YatzeeGame.AMOUNT_DICE];
        for (int index : keepIndices) {
            keepings[index - 1] = true;
        }
    }

    void apply(Dice[] current, Dice[] previous) {
        for (int i = 0; i < YatzeeGame.AMOUNT_DICE; i++) {
            if (keepings[i]) {
                current[i] = previous[i];
            }
        }
    }

    boolean[] getKeepings() {
        return keepings;
    }
}
