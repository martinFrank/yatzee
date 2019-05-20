package com.github.martinfrank.yahtzee;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RollCalculator {

    private RollCalculator() {

    }

    public static int getValue(RowType type, Roll roll) {
        switch (type) {
            case ONE:
                return countNumbers(1, roll);
            case TWO:
                return countNumbers(2, roll);
            case THREE:
                return countNumbers(3, roll);
            case FOUR:
                return countNumbers(4, roll);
            case FIVE:
                return countNumbers(5, roll);
            case SIX:
                return countNumbers(6, roll);
            case ONE_PAIR:
                return countOnePair(roll);
            case TWO_PAIR:
                return countTwoPair(roll);
            case THREE_OF_A_KIND:
                return countThreeOfAKind(roll);
            case FOUR_OF_A_KIND:
                return countFourOfAKind(roll);
            case FULL_HOUSE:
                return countFullHouse(roll);
            case MINOR_STRAIGHT:
                return countMinorStraight(roll);
            case MAJOR_STRAIGHT:
                return countMajorStraight(roll);
            case YATZEE:
                return countYatzee(roll);
            case CHANCE:
                return countAll(roll);
            default: return 0;
        }
    }

    private static int countOnePair(Roll roll) {
        return isOnePair(roll) ? countAll(roll) : 0;
    }

    private static int countTwoPair(Roll roll) {
        return isTwoPair(roll) ? countAll(roll) : 0;
    }

    private static int countThreeOfAKind(Roll roll) {
        return isThreeOfAKind(roll) ? countAll(roll) : 0;
    }

    private static int countFourOfAKind(Roll roll) {
        return isFourOfAKind(roll) ? countAll(roll) : 0;
    }

    private static int countFullHouse(Roll roll) {
        return isFullHouse(roll) ? 30 : 0;
    }

    private static int countMinorStraight(Roll roll) {
        return isMinorStraight(roll) ? 35 : 0;
    }

    private static int countMajorStraight(Roll roll) {
        return isMajorStraight(roll) ? 40 : 0;
    }

    private static int countYatzee(Roll roll) {
        return isYatzee(roll) ? 50 : 0;
    }

    private static boolean isYatzee(Roll roll) {
        Dice[] dice = roll.getDice();
        return dice[0].getEyes() == dice[1].getEyes() &&
                dice[1].getEyes() == dice[2].getEyes() &&
                dice[2].getEyes() == dice[3].getEyes() &&
                dice[3].getEyes() == dice[4].getEyes();
    }

    private static boolean isMajorStraight(Roll roll) {
        Set<Integer> set = getDiceSet(roll.getDice());
        boolean fromOne = true;
        boolean fromTwo = true;
        for (int i = 1; i <= 5; i++) {
            if (!set.contains(i)) {
                fromOne = false;
            }
            if (!set.contains(i + 1)) {
                fromTwo = false;
            }
        }
        return fromOne || fromTwo;

    }

    private static boolean isMinorStraight(Roll roll) {
        Set<Integer> set = getDiceSet(roll.getDice());
        boolean fromOne = true;
        boolean fromTwo = true;
        boolean fromThree = true;
        for (int i = 1; i <= 4; i++) {
            if (!set.contains(i)) {
                fromOne = false;
            }
            if (!set.contains(i + 1)) {
                fromTwo = false;
            }
            if (!set.contains(i + 2)) {
                fromThree = false;
            }
        }
        return fromOne || fromTwo || fromThree;
    }

    private static Set<Integer> getDiceSet(Dice[] dices) {
        Set<Integer> set = new HashSet<>();
        for (Dice dice : dices) {
            set.add(dice.getEyes());
        }
        return set;
    }

    private static boolean isFullHouse(Roll roll) {
        int[] sorted = getSorted(roll.getDice());
        if (sorted[0] == sorted[1] &&
                sorted[2] == sorted[3] &&
                sorted[3] == sorted[4]) {
            return true;
        }
        return sorted[0] == sorted[1] &&
                sorted[1] == sorted[2] &&
                sorted[3] == sorted[4];
    }

    private static boolean isFourOfAKind(Roll roll) {
        int[] sorted = getSorted(roll.getDice());
        for (int i = 3; i < 5; i++) {
            if (sorted[i - 3] == sorted[i] && sorted[i - 2] == sorted[i] && sorted[i - 1] == sorted[i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThreeOfAKind(Roll roll) {
        int[] sorted = getSorted(roll.getDice());
        for (int i = 2; i < 5; i++) {
            if (sorted[i - 2] == sorted[i] && sorted[i - 1] == sorted[i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTwoPair(Roll roll) {
        int[] sorted = getSorted(roll.getDice());
        int firstPairIndex = -1;
        for (int i = 1; i < 5; i++) {
            if (sorted[i - 1] == sorted[i]) {
                firstPairIndex = i;
                break;
            }
        }
        if (firstPairIndex >= 1 && firstPairIndex <= 3) {
            for (int i = firstPairIndex + 1; i < 5; i++) {
                if (sorted[i - 1] == sorted[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isOnePair(Roll roll) {
        int[] sorted = getSorted(roll.getDice());
        for (int i = 1; i < 5; i++) {
            if (sorted[i - 1] == (sorted[i])) {
                return true;
            }
        }
        return false;
    }

    private static int countAll(Roll roll) {
        return Arrays.stream(roll.getDice()).mapToInt(Dice::getEyes).sum();
    }

    private static int countNumbers(int number, Roll roll) {
        int value = 0;
        for (int i = 0; i < YahtzeeGame.AMOUNT_DICE; i++) {
            Dice[] dice = roll.getDice();
            if (dice[i].getEyes() == number) {
                value = value + number;
            }
        }
        return value;
    }

    private static int[] getSorted(Dice[] dice) {
        int[] copy = Arrays.stream(dice).mapToInt(Dice::getEyes).toArray();
        Arrays.sort(copy);
        return copy;
    }

}
