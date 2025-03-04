package com.keyfull.modes;

import static com.keyfull.Parameters.CELL;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.keyfull.Parameters;
import com.keyfull.adapters.DragCoordinates;  

public class Drag {
    public static void drag(DragCoordinates dragCoordinates, Parameters params) {
        try {
            int[] startCoords = calculateCoordinates(dragCoordinates.getStart(), params);
            int[] endCoords = calculateCoordinates(dragCoordinates.getEnd(), params);
            
            // Create a Robot instance
            Robot robot = new Robot();
            
            // Move mouse to start position
            robot.mouseMove(startCoords[0], startCoords[1]);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            
            // Simulate dragging to end position
            robot.mouseMove(endCoords[0], endCoords[1]);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    
    private static int[] calculateCoordinates(int[] position, Parameters params) {
        int row = position[0];
        int col = position[1];
        char target = (char) position[2];
        int frameX = position[3];
        int frameY = position[4];

        // Find the character in the CELL grid
        for (String cellRow : CELL) {
            int charIndex = cellRow.indexOf(target);
            if (charIndex != -1) {
                col = charIndex;
                row = CELL.indexOf(cellRow);
                break;
            }
        }

        int x = (int) (frameX + (col * params.SUB_SQUARE_WIDTH) + params.SUB_SQUARE_WIDTH / 2);
        int y = (int) (frameY + (row * params.SUB_SQUARE_HEIGHT) - params.SUB_SQUARE_HEIGHT / 2);
        return new int[]{x, y};
    }
}
