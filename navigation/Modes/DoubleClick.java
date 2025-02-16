package navigation.Modes;

import static navigation.Parameters.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class DoubleClick {
    public static void double_click(char target) {
        try {
            double x = highlight_width();
            double y = highlight_height() - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;
            
            for (String row : CELL) {
                if (row.indexOf(target) != -1) {
                   x += row.indexOf(target) * SUB_SQUARE_WIDTH + SUB_SQUARE_WIDTH / 2;
                   y += CELL.indexOf(row) * SUB_SQUARE_HEIGHT + SUB_SQUARE_HEIGHT / 2;
                }
            }
            
            Robot robot = new Robot();
            robot.mouseMove((int) x, (int) y);

            // First Click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // Small delay before second click
            Thread.sleep(100); 

            // Second Click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}