package src.main.java.com.keyfull.modes;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import src.main.java.com.keyfull.Parameters;
import static src.main.java.com.keyfull.Parameters.CELL;

public class Click {
    public static void click(char target, int offsetX, int offsetY, Parameters params) {
        try {
            double x = params.highlight_width();
            double y = params.highlight_height() - params.SUB_SQUARE_HEIGHT * 2 + params.SQUARE_HEIGHT * 0.95;

            for (String row : CELL) {
                if (row.indexOf(target) != -1) {
                    x += row.indexOf(target) * params.SUB_SQUARE_WIDTH;
                    y += CELL.indexOf(row) * params.SUB_SQUARE_HEIGHT;
                }
            }

            // Adjust for multi-monitor setup
            x += offsetX + params.SUB_SQUARE_WIDTH / 2;
            y += offsetY - params.SUB_SQUARE_HEIGHT / 2;

            // Create a Robot instance
            Robot robot = new Robot();

            // Move the mouse to the calculated coordinates
            robot.mouseMove((int) x, (int) y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
