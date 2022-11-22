package lec01_01;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HiddenBoxTest {
    private static Box box = null;
    private static Ball[] b = null;

    private static final int NUM_BALLS_TO_TEST = 5;
    private static final int BOX_CAPACITY = NUM_BALLS_TO_TEST - 1;
    private static final double BALL_UNIT_VOLUME = 10.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;
    private static final int TRIES_FOR_BALLS_TEST = 3;
    private static final Color[] COLORS = {Color.BLACK, Color.BLUE,
            Color.CYAN, new Color(255, 0, 0)};
    private static final int COLOR_COUNT = COLORS.length;

    @BeforeClass
    public static void setupBeforeTests() throws Exception {

        assertTrue("Test case error, you must test at least 1 Ball!!",
                NUM_BALLS_TO_TEST > 0);
        assertTrue("This test case is set up assuming that the box cannot contain all the balls, please check and change parameters!",
                NUM_BALLS_TO_TEST > BOX_CAPACITY);
        double box_volume = 0;

        // Let's create the balls we need.
        b = new Ball[NUM_BALLS_TO_TEST];
        for (int i = 0; i < NUM_BALLS_TO_TEST; i++) {
            if (i < BOX_CAPACITY) {
                box_volume += (i + 1) * BALL_UNIT_VOLUME;
            }
            b[i] = new Ball((i + 1) * BALL_UNIT_VOLUME, COLORS[i % COLOR_COUNT]);
        }

        // Now, we create the box once we figure out how big a box we
        // need.
        box = new Box(box_volume);
    }
    @Test
    public void testBallsWithEqualWeight() {
        Random rnd = new Random();

        for (int k=0; k<TRIES_FOR_BALLS_TEST; k++) {

            box.clear();

            // Let's put all the balls but the last into a list.
            LinkedList<Ball> list = new LinkedList<Ball>();
            LinkedList<Ball> sorted = new LinkedList<Ball>();
            for (int i=0; i<BOX_CAPACITY-1; i++) {
                list.add(b[i]);
            }
            // Now, let's add a new ball, with the same volume as the 2nd ball.
            int index = 1;
            assertTrue(index>=0 && index<BOX_CAPACITY);
            Ball newBall = new Ball(b[index].getVolume(),
                    b[index].getColor());
            list.add(newBall);

            for (int i=0; i<index+1; i++) {
                sorted.add(b[i]);
            }
            sorted.add(newBall);
            for (int i=index+1; i<BOX_CAPACITY-1; i++) {
                sorted.add(b[i]);
            }

            // First we add the balls to the box in some random order.
            for (int i=0; i<BOX_CAPACITY; i++) {
                box.add(list.remove(rnd.nextInt(list.size())));
            }

            int contentsSize = box.size();
            // Next we call the iterator and check that the balls come out
            // in the correct order.
            Iterator<Ball> it = box.getBallsFromSmallest();
            int count = 0;
            while (it.hasNext() && count < BOX_CAPACITY) {
                Ball ball = it.next();
                assertEquals("Balls not in correct order",
                        sorted.get(count).getVolume(), ball.getVolume(), JUNIT_DOUBLE_DELTA);
                count++;
            }
            assertEquals("Box.getBallsFromSmallest() did not return all the balls",
                    BOX_CAPACITY, count);
            assertEquals("Number of balls in box was modified",
                    contentsSize, box.size());
        }
    }

}
