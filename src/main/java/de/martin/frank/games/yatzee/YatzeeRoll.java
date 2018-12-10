package de.martin.frank.games.yatzee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class YatzeeRoll {

    private Integer[] dice;

    private static final Random RANDOM = new Random();

    public YatzeeRoll(Integer[] dice) {
        this.dice = dice;
    }

    public YatzeeRoll() {
        dice = new Integer[5];
        for (int i = 0; i < 5; i++) {
            dice[i] = RANDOM.nextInt(6) + 1;
        }
    }

    public int getValue(YatzeeRowType type) {
        switch (type) {
            case ONE:
                return countNumbers(1);
            case TWO:
                return countNumbers(2);
            case THREE:
                return countNumbers(3);
            case FOUR:
                return countNumbers(4);
            case FIVE:
                return countNumbers(5);
            case SIX:
                return countNumbers(6);
            case ONE_PAIR:
                return isOnePair() ? countAll() : 0;
            case TWO_PAIR:
                return isTwoPair() ? countAll() : 0;
            case THREE_OF_A_KIND:
                return isThreeOfAKind() ? countAll() : 0;
            case FOUR_OF_A_KIND:
                return isFourOfAKind() ? countAll() : 0;
            case FULL_HOUSE:
                return isFullHouse() ? 30 : 0;
            case MINOR_STRAIGHT:
                return isMinorStraight() ? 35 : 0;
            case MAJOR_STRAIGHT:
                return isMajorStraight() ? 40 : 0;
            case YATZEE:
                return isYatzee() ? 50 : 0;
            case CHANCE:
                return countAll();
        }
        return 0;
    }

    void keep(YatzeeRoll prevThrow, Boolean[] keep) {
        for (int i = 0; i < 5; i++) {
            if (keep[i] != null && keep[i]) {
                dice[i] = prevThrow.dice[i];
            }
        }
    }

    private boolean isYatzee() {
        return dice[0].equals(dice[1]) &&
                dice[1].equals(dice[2]) &&
                dice[2].equals(dice[3]) &&
                dice[3].equals(dice[4]);
    }

    private boolean isMajorStraight() {
        Set<Integer> set = new HashSet<>(Arrays.asList(dice));
        boolean fromOne = true;
        boolean fromTwo = true;
        for(int i = 1; i <= 5; i ++){
            if (!set.contains(i)){
                fromOne = false;
            }
            if (!set.contains(i + 1)){
                fromTwo = false;
            }
        }
        return fromOne || fromTwo;

    }

    private boolean isMinorStraight() {
        Set<Integer> set = new HashSet<>(Arrays.asList(dice));
        boolean fromOne = true;
        boolean fromTwo = true;
        boolean fromThree = true;
        for(int i = 1; i <= 4; i ++){
            if (!set.contains(i)){
                fromOne = false;
            }
            if (!set.contains(i + 1)){
                fromTwo = false;
            }
            if (!set.contains(i + 2)){
                fromThree = false;
            }
        }
        return fromOne || fromTwo ||fromThree;
    }

    private boolean isFullHouse() {
        Integer[] sorted = getSorted();
        if (sorted[0].equals(sorted[1])  &&
                sorted[2].equals(sorted[3]) &&
                sorted[3].equals(sorted[4])) {
            return true;
        }
        return sorted[0].equals(sorted[1]) &&
                sorted[1].equals(sorted[2]) &&
                sorted[3].equals(sorted[4]);
    }

    private boolean isFourOfAKind() {
        Integer[] sorted = getSorted();
        for (int i = 3; i < 5; i++) {
            if (sorted[i - 3].equals(sorted[i]) && sorted[i - 2].equals(sorted[i]) && sorted[i - 1].equals(sorted[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isThreeOfAKind() {
        Integer[] sorted = getSorted();
        for (int i = 2; i < 5; i++) {
            if (sorted[i - 2].equals(sorted[i]) && sorted[i - 1].equals(sorted[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean isTwoPair() {
        Integer[] sorted = getSorted();
        int firstPairIndex = -1;
        for (int i = 1; i < 5; i++) {
            if (sorted[i - 1].equals(sorted[i])) {
                firstPairIndex = i;
                break;
            }
        }
        if (firstPairIndex >= 1 && firstPairIndex <= 3) {
            for (int i = firstPairIndex + 1; i < 5; i++) {
                if (sorted[i - 1].equals(sorted[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnePair() {
        Integer[] sorted = getSorted();
        for (int i = 1; i < 5; i++) {
            if (sorted[i - 1].equals(sorted[i])) {
                return true;
            }
        }
        return false;
    }

    private int countAll() {
        return Arrays.stream(dice).mapToInt(i -> i).sum();
    }

    private int countNumbers(int number) {
        int value = 0;
        for (int i = 0; i < 5; i++) {
            if (dice[i] == number) {
                value = value + number;
            }
        }
        return value;
    }

    private Integer[] getSorted() {
        Integer[] copy = Arrays.copyOf(dice, 5);
        Arrays.sort(copy);
        return copy;
    }

    @Override
    public String toString() {
        return Arrays.stream(dice).map(d -> "[" + Integer.toString(d) + "]").collect(Collectors.joining());
    }
}
