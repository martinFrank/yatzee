package de.elite.games.yatzee;

import java.util.HashSet;
import java.util.Set;

public class Keeping {

    private boolean[] keepings;

    Keeping() {
        this(new HashSet<>());
    }

    public Keeping(Set<Integer> keepIndices) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < YatzeeGame.AMOUNT_DICE; i++) {
            sb.append(keepings[i] ? "k" : " ");
            if (i < YatzeeGame.AMOUNT_DICE - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
