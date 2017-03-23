package Cube;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shurik on 05.03.2017.
 */
public class AllSidesTest {
    @Test
    public void rotateFrontFirst() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, new int[]{1});
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, new int[]{1});
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, new int[]{1});
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, new int[]{1});
        Assert.assertEquals(result, cubeMain);
    }

    @Test
    public void rotateBackCenter() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.rotate(Side.BACK, Direction.CLOCKWISE, new int[]{2});
        result.rotate(Side.BACK, Direction.CLOCKWISE, new int[]{2});
        result.rotate(Side.BACK, Direction.CLOCKWISE, new int[]{2});
        result.rotate(Side.BACK, Direction.CLOCKWISE, new int[]{2});
        Assert.assertEquals(result, cubeMain);
    }

    @Test
    public void rotateALL() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.rotate(Side.RIGHT, Direction.COUNTERCLOCKWISE, new int[]{2, 1, 3});
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, new int[]{1, 2, 3});
        result.rotate(Side.LEFT, Direction.CLOCKWISE, new int[]{3, 2, 1});
        result.rotate(Side.BACK, Direction.CLOCKWISE, new int[]{1, 3, 2});
        Assert.assertEquals(result, cubeMain);
    }

    @Test
    public void backToFront() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.moveToFront(Side.BACK);
        result.rotate(Side.LEFT, Direction.CLOCKWISE, new int[]{1, 2, 3});
        result.rotate(Side.LEFT, Direction.CLOCKWISE, new int[]{3, 2, 1});
        Assert.assertEquals(result, cubeMain);
    }

    @Test
    public void upAndDownSideToFront() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.moveToFront(Side.UPSIDE);
        result.rotate(Side.FRONT, Direction.CLOCKWISE, new int[]{1, 3});
        result.rotate(Side.FRONT, Direction.CLOCKWISE, new int[]{3, 1});
        result.moveToFront(Side.DOWNSIDE);
        result.rotate(Side.DOWNSIDE, Direction.COUNTERCLOCKWISE, new int[]{1, 3});
        result.rotate(Side.DOWNSIDE, Direction.COUNTERCLOCKWISE, new int[]{3, 1});
        Assert.assertEquals(result, cubeMain);
    }
}