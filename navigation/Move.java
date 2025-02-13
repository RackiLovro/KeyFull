package navigation;

import static navigation.Parameters.*;

import java.awt.AWTException;
import java.awt.Robot;

public class Move {
	protected static void move(char target) {
        try {
	        double x = HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
	        double y = HIGHLIGHT_ROW * SQUARE_HEIGHT - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;
	        
    		for (String row : CELL) {
    			if (row.indexOf(target) != -1) {
    		       x += row.indexOf(target) * SUB_SQUARE_WIDTH;
    		       y += CELL.indexOf(row) * SUB_SQUARE_HEIGHT;
    			}
    		}
    		
            // Create a Robot instance
            Robot robot = new Robot();
            
            // Move the mouse to the calculated coordinates
            robot.mouseMove((int) x, (int) y);
        } catch (AWTException e) {
            e.printStackTrace();
        }
	}
}
