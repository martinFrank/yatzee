package com.github.martinfrank.yahtzee;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple YahtzeeApp.
 */
public class YatzeeAppTest {

    @Test
    public void testNumber() {
        assertEquals(1, RollCalculator.getValue(RowType.ONE, createRoll(1, 2, 3, 4, 5)));
        assertEquals(2, RollCalculator.getValue(RowType.ONE, createRoll(1, 1, 3, 4, 5)));
        assertEquals(2, RollCalculator.getValue(RowType.ONE, createRoll(1, 3, 4, 5, 1)));
        assertEquals(0, RollCalculator.getValue(RowType.ONE, createRoll(5, 3, 4, 5, 5)));
        assertEquals(2, RollCalculator.getValue(RowType.TWO, createRoll(1, 3, 4, 2, 1)));

        assertEquals(3, RollCalculator.getValue(RowType.THREE, createRoll(1, 3, 4, 5, 1)));
        assertEquals(4, RollCalculator.getValue(RowType.FOUR, createRoll(1, 3, 4, 5, 1)));
        assertEquals(5, RollCalculator.getValue(RowType.FIVE, createRoll(1, 3, 4, 5, 1)));
        assertEquals(6, RollCalculator.getValue(RowType.SIX, createRoll(1, 3, 6, 5, 1)));
    }

    @Test
    public void testOnePair() {
        assertEquals(0, RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 2, 3, 4, 5)));
        assertEquals(14, RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 1, 3, 4, 5)));
        assertEquals(14, RollCalculator.getValue(RowType.ONE_PAIR, createRoll(1, 3, 4, 5, 1)));
    }

    @Test
    public void testTwoPair() {
        assertEquals(0, RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 2, 3, 4, 5)));
        assertEquals(13, RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 1, 3, 4, 4)));
        assertEquals(13, RollCalculator.getValue(RowType.TWO_PAIR, createRoll(1, 3, 4, 4, 1)));
    }

    @Test
    public void testThreeOfAKind() {
        assertEquals(8, RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 1, 1, 2, 3)));
        assertEquals(8, RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 1, 1, 2, 3)));
        assertEquals(8, RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(1, 3, 1, 2, 1)));
        assertEquals(0, RollCalculator.getValue(RowType.THREE_OF_A_KIND, createRoll(3, 2, 1, 2, 1)));

    }

    @Test
    public void testFourOfAKind() {
        assertEquals(7, RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 1, 1, 1, 3)));
        assertEquals(7, RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 1, 1, 3, 1)));
        assertEquals(0, RollCalculator.getValue(RowType.FOUR_OF_A_KIND, createRoll(1, 2, 1, 2, 1)));
    }

    @Test
    public void testFullHouse() {
        assertEquals(30, RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 1, 2, 2, 2)));
        assertEquals(30, RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 1, 1, 2, 2)));
        assertEquals(30, RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 2, 1, 2, 1)));
        assertEquals(0, RollCalculator.getValue(RowType.FULL_HOUSE, createRoll(1, 2, 3, 2, 1)));
    }

    @Test
    public void testStraight() {
        assertEquals(35, RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(1, 2, 3, 4, 6)));
        assertEquals(35, RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 5, 6)));
        assertEquals(35, RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 5, 3)));
        assertEquals(0, RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(2, 3, 4, 2, 3)));
        assertEquals(0, RollCalculator.getValue(RowType.MINOR_STRAIGHT, createRoll(1, 2, 3, 5, 6)));

        assertEquals(40, RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(1, 2, 3, 4, 5)));
        assertEquals(40, RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(2, 3, 4, 5, 6)));
        assertEquals(0, RollCalculator.getValue(RowType.MAJOR_STRAIGHT, createRoll(1, 2, 3, 5, 6)));

    }

    @Test
    public void testYatzee() {
        assertEquals(50, RollCalculator.getValue(RowType.YATZEE, createRoll(1, 1, 1, 1, 1)));
        assertEquals(50, RollCalculator.getValue(RowType.YATZEE, createRoll(2, 2, 2, 2, 2)));
        assertEquals(0, RollCalculator.getValue(RowType.YATZEE, createRoll(2, 2, 2, 2, 1)));
    }

    @Test
    public void testChance() {
        assertEquals(17, RollCalculator.getValue(RowType.CHANCE, createRoll(1, 2, 3, 5, 6)));
    }

    private Roll createRoll(int a, int b, int c, int d, int e) {
        Dice[] dices = new Dice[]{new Dice(a), new Dice(b), new Dice(c), new Dice(d), new Dice(e)};
        return new Roll(dices);
    }

    @Test
    public void mainTest() {
        try {
            InputStream original = System.in;
            System.setIn(new ByteArrayInputStream("exit\n".getBytes()));
            YahtzeeApp.main(new String[]{});
            System.setIn(original);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
