package src.main.java.com.keyfull.modes;

import static src.main.java.com.keyfull.Parameters.CELL;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import src.main.java.com.keyfull.Parameters;

public class DoubleClick {
    public static void double_click(char target, Parameters params) {
        try {
            double x = params.highlight_width();
            double y = params.highlight_height() - params.SUB_SQUARE_HEIGHT * 2 + params.SQUARE_HEIGHT * 0.95;
            
            for (String row : CELL) {
                if (row.indexOf(target) != -1) {
                   x += row.indexOf(target) * params.SUB_SQUARE_WIDTH + params.SUB_SQUARE_WIDTH / 2;
                   y += CELL.indexOf(row) * params.SUB_SQUARE_HEIGHT + params.SUB_SQUARE_HEIGHT / 2;
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