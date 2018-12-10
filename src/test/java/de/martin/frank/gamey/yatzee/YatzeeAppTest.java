package de.martin.frank.gamey.yatzee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.martin.frank.games.yatzee.YatzeeRoll;
import de.martin.frank.games.yatzee.YatzeeRow;
import de.martin.frank.games.yatzee.YatzeeRowType;
import org.junit.Test;

/**
 * Unit test for simple YatzeeApp.
 */
public class YatzeeAppTest
{

    @Test
    public void testNumber()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,4,5}).getValue(YatzeeRowType.ONE), 1);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,3,4,5}).getValue(YatzeeRowType.ONE), 2);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,5,1}).getValue(YatzeeRowType.ONE), 2);
        assertEquals( new YatzeeRoll(new Integer[]{5,3,4,5,5}).getValue(YatzeeRowType.ONE), 0);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,2,1}).getValue(YatzeeRowType.TWO), 2);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,5,1}).getValue(YatzeeRowType.THREE), 3);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,5,1}).getValue(YatzeeRowType.FOUR), 4);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,5,1}).getValue(YatzeeRowType.FIVE), 5);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,6,5,1}).getValue(YatzeeRowType.SIX), 6);
    }

    @Test
    public void testOnePair()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,4,5}).getValue(YatzeeRowType.ONE_PAIR), 0);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,3,4,5}).getValue(YatzeeRowType.ONE_PAIR), 14);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,5,1}).getValue(YatzeeRowType.ONE_PAIR), 14);
    }

    @Test
    public void testTwoPair()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,4,5}).getValue(YatzeeRowType.TWO_PAIR), 0);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,3,4,4}).getValue(YatzeeRowType.TWO_PAIR), 13);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,4,4,1}).getValue(YatzeeRowType.TWO_PAIR), 13);
    }

    @Test
    public void testThreeOfAKind()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,1,1,2,3}).getValue(YatzeeRowType.THREE_OF_A_KIND), 8);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,1,2,3}).getValue(YatzeeRowType.THREE_OF_A_KIND), 8);
        assertEquals( new YatzeeRoll(new Integer[]{1,3,1,2,1}).getValue(YatzeeRowType.THREE_OF_A_KIND), 8);
        assertEquals( new YatzeeRoll(new Integer[]{3,2,1,2,1}).getValue(YatzeeRowType.THREE_OF_A_KIND), 0);
    }

    @Test
    public void testFourOfAKind()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,1,1,1,3}).getValue(YatzeeRowType.FOUR_OF_A_KIND), 7);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,1,3,1}).getValue(YatzeeRowType.FOUR_OF_A_KIND), 7);
        assertEquals( new YatzeeRoll(new Integer[]{1,2,1,2,1}).getValue(YatzeeRowType.FOUR_OF_A_KIND), 0);
    }

    @Test
    public void testFullHouse()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,1,2,2,2}).getValue(YatzeeRowType.FULL_HOUSE), 30);
        assertEquals( new YatzeeRoll(new Integer[]{1,1,1,2,2}).getValue(YatzeeRowType.FULL_HOUSE), 30);
        assertEquals( new YatzeeRoll(new Integer[]{1,2,1,2,1}).getValue(YatzeeRowType.FULL_HOUSE), 30);
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,2,1}).getValue(YatzeeRowType.FULL_HOUSE), 0);
    }

    @Test
    public void testStraight()
    {
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,4,6}).getValue(YatzeeRowType.MINOR_STRAIGHT), 35);
        assertEquals( new YatzeeRoll(new Integer[]{2,3,4,5,6}).getValue(YatzeeRowType.MINOR_STRAIGHT), 35);
        assertEquals( new YatzeeRoll(new Integer[]{2,3,4,5,3}).getValue(YatzeeRowType.MINOR_STRAIGHT), 35);
        assertEquals( new YatzeeRoll(new Integer[]{2,3,4,2,3}).getValue(YatzeeRowType.MINOR_STRAIGHT), 0);
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,5,6}).getValue(YatzeeRowType.MINOR_STRAIGHT), 0);

        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,4,5}).getValue(YatzeeRowType.MAJOR_STRAIGHT), 40);
        assertEquals( new YatzeeRoll(new Integer[]{2,3,4,5,6}).getValue(YatzeeRowType.MAJOR_STRAIGHT), 40);
        assertEquals( new YatzeeRoll(new Integer[]{1,2,3,5,6}).getValue(YatzeeRowType.MAJOR_STRAIGHT), 0);
    }
}
