package Cube;

import org.junit.Assert;
import org.junit.Test;
import Cube.*;
import Cube.AllSides.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by shurik on 05.03.2017.
 */
public class AllSidesTest {
    @Test
    public void rotateFrontFirst() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.shuffle();
        String ready = cubeMain.toString();
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, "1");
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, "1");
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, "1");
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, "1");
        Assert.assertEquals(result.toString(), ready);
    }

    @Test
    public void rotateBackCenter() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.shuffle();
        String ready = cubeMain.toString();
        result.rotate(Side.BACK, Direction.CLOCKWISE, "2");
        result.rotate(Side.BACK, Direction.CLOCKWISE, "2");
        result.rotate(Side.BACK, Direction.CLOCKWISE, "2");
        result.rotate(Side.BACK, Direction.CLOCKWISE, "2");
        Assert.assertEquals(result.toString(), ready);
    }

    @Test
    public void rotateALL() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.shuffle();
        String ready = cubeMain.toString();
        result.rotate(Side.RIGHT, Direction.COUNTERCLOCKWISE, "2,1,3");
        result.rotate(Side.FRONT, Direction.COUNTERCLOCKWISE, "1,2,3");
        result.rotate(Side.LEFT, Direction.CLOCKWISE, "3,2,1");
        result.rotate(Side.BACK, Direction.CLOCKWISE, "1,3,2");
        Assert.assertEquals(result.toString(), ready);
    }

    @Test
    public void backToFront() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.shuffle();
        String ready = cubeMain.toString();
        result.moveToFront(Side.BACK);
        result.rotate(Side.LEFT, Direction.CLOCKWISE, "1,2,3");
        result.rotate(Side.LEFT, Direction.CLOCKWISE, "3,2,1");
        Assert.assertEquals(result.toString(), ready);
    }

    @Test
    public void upAndDownSideToFront() {
        AllSides result = new AllSides();
        AllSides cubeMain = new AllSides();
        result.shuffle();
        String ready = cubeMain.toString();
        result.moveToFront(Side.UPSIDE);
        result.rotate(Side.FRONT, Direction.CLOCKWISE, "1,3");
        result.rotate(Side.FRONT, Direction.CLOCKWISE, "3,1");
        result.moveToFront(Side.DOWNSIDE);
        result.rotate(Side.DOWNSIDE, Direction.COUNTERCLOCKWISE, "1,3");
        result.rotate(Side.DOWNSIDE, Direction.COUNTERCLOCKWISE, "3,1");
        Assert.assertEquals(result.toString(), ready);
    }
}