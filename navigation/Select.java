package navigation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Select {
    public static void select(char start, char end) {
        try {
            // Create a Robot instance
            Robot robot = new Robot();

            // Move to the starting position
            Move.move(start);

            // Press and hold the mouse button
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

            // Move to the target position
            Move.move(end);

            // Release the mouse button
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
