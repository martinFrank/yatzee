package de.elite.games.yatzee;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple YatzeeApp.
 */
public class YatzeeAppTest {

    @Test
    public void testNumber() {
        assertEquals(RollCalculator.getValue(RowType.ONE, createRoll(1, 2, 3, 4, 5)), 1);
        assertEquals(RollCalculator.getValue(RowType.ONE, createRoll(1, 1, 3, 4, 5)), 2);
        assertEquals(RollCalculator.getValue(RowType.ONE, createRoll(1, 3, 4, 5, 1)), 2);
        assertEquals(RollCalculator.getValue(RowType.ONE, createRoll(5, 3, 4, 5, 5)), 0);
        assertEquals(RollCalculator.getValue(RowType.TWO, createRoll(1, 3, 4, 2, 1)), 2);

        assertEquals(RollCalculator.getValue(RowType.THREE, createRoll(1, 3, 4, 5, 1)), 3);
        assertEquals(RollCalculator.getValue(RowType.FOUR, createRoll(1, 3, 4, 5, 1)), 4);
        assertEquals(RollCalculator.getValue(RowType.FIVE, createRoll(1, 3, 4, 5, 1)), 5);
        assertEquals(RollCalculator.getValue(RowType.SIX, createRoll(1, 3, 6, 5, 1)), 6);
    }

    @Test
    public void testOnePair() {
        assertEquals(RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 2, 3, 4, 5)), 0);
        assertEquals(RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 1, 3, 4, 5)), 14);
        assertEquals(RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 3, 4, 5, 1)), 14);
    }

    @Test
    public void testTwoPair() {
        assertEquals(RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 2, 3, 4, 5)), 0);
        assertEquals(RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 1, 3, 4, 4)), 13);
        assertEquals(RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 3, 4, 4, 1)), 13);
    }

    @Test
    public void testThreeOfAKind() {
        assertEquals(RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 1, 1, 2, 3)), 8);
        assertEquals(RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 1, 1, 2, 3)), 8);
        assertEquals(RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 3, 1, 2, 1)), 8);
        assertEquals(RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(3, 2, 1, 2, 1)), 0);

    }

    @Test
    public void testFourOfAKind() {
        assertEquals(RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 1, 1, 1, 3)), 7);
        assertEquals(RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 1, 1, 3, 1)), 7);
        assertEquals(RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 2, 1, 2, 1)), 0);
    }

    @Test
    public void testFullHouse() {
        assertEquals(RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 1, 2, 2, 2)), 30);
        assertEquals(RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 1, 1, 2, 2)), 30);
        assertEquals(RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 2, 1, 2, 1)), 30);
        assertEquals(RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 2, 3, 2, 1)), 0);
    }

    @Test
    public void testStraight() {
        assertEquals(RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(1, 2, 3, 4, 6)), 35);
        assertEquals(RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 5, 6)), 35);
        assertEquals(RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 5, 3)), 35);
        assertEquals(RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 2, 3)), 0);
        assertEquals(RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(1, 2, 3, 5, 6)), 0);

        assertEquals(RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(1, 2, 3, 4, 5)), 40);
        assertEquals(RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(2, 3, 4, 5, 6)), 40);
        assertEquals(RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(1, 2, 3, 5, 6)), 0);

    }

    @Test
    public void testYatzee() {
        assertEquals(RollCalculator.getValue(RowType.YATZEE, createRoll(1, 1, 1, 1, 1)), 50);
        assertEquals(RollCalculator.getValue(RowType.YATZEE, createRoll(2, 2, 2, 2, 2)), 50);
        assertEquals(RollCalculator.getValue(RowType.YATZEE, createRoll(2, 2, 2, 2, 1)), 0);
    }

    @Test
    public void testChance() {
        assertEquals(RollCalculator.getValue(RowType.CHANCE, createRoll(1, 2, 3, 5, 6)), 17);
    }

    private Roll createRoll(int a, int b, int c, int d, int e) {
        Dice[] dices = new Dice[]{new Dice(a), new Dice(b), new Dice(c), new Dice(d), new Dice(e)};
        return new Roll(dices);
    }
}
