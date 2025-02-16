package navigation.Modes;

import static navigation.Parameters.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Move {
	public static void move(char target) {
        try {
	        double x = highlight_width();
	        double y = highlight_height() - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;
	        
    		for (String row : CELL) {
    			if (row.indexOf(target) != -1) {
    		       x += row.indexOf(target) * SUB_SQUARE_WIDTH + SUB_SQUARE_WIDTH / 2;
    		       y += CELL.indexOf(row) * SUB_SQUARE_HEIGHT + SUB_SQUARE_HEIGHT / 2;
    			}
    		}
    		
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
