package navigation.Modes;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.MouseInfo;
import java.awt.Point;

public class Drag {
    public static void drag(char start, char end) {
        try {
            Robot robot = new Robot();

            // Move to start position
            Click.click(start);
            robot.delay(200); // Allow UI to settle

            // Press and hold mouse button
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(150); // Slight delay to ensure button is recognized

            // Small nudge to trigger drag
            Point currentPosition = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(currentPosition.x + 1, currentPosition.y);
            robot.delay(50);

            // Move to end position
            Click.click(end);
            robot.delay(200); // Give time for drag to complete

            // Release mouse button
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(100); // Small delay before finishing

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
