package navigation;

import static navigation.Parameters.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Mesh {
    JPanel gridPanel;
    
    public Mesh(JFrame frame) {
        // Create a custom JPanel for the grid
        gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Mesh.grid(g2d);
                Mesh.bigletters(g2d);
                
                // Highlight a rectangle if specified
                if (HIGHLIGHT_ROW >= 0 && HIGHLIGHT_COLUMN >= 0) {
                	Mesh.erase_cell(g2d, HIGHLIGHT_COLUMN, HIGHLIGHT_COLUMN);
                	Mesh.smallletters(g2d);
                }
            }
        };
        // Set grid panel to be transparent
        gridPanel.setOpaque(false);
        
        // Add the grid panel to the frame
        frame.add(gridPanel);
    }
  
	public static void smallletters(Graphics2D g2d) {
        double y = HIGHLIGHT_ROW * SQUARE_HEIGHT - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;

    	for(int subrow = 0; subrow < SUBROWS; subrow++) {
            double x = HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
            
    		for(int subcolumn = 0; subcolumn < SUBCOLUMNS * 2; subcolumn++) {
                g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SUB_SQUARE_HEIGHT));
                g2d.setColor(MAIN_COLOR);
                g2d.drawString(String.valueOf(CELL.get(subrow).charAt(subcolumn)), (float) x, (float) y);

                x += SUB_SQUARE_WIDTH;
    		}
    		y += SUB_SQUARE_HEIGHT;
    	}
    }

	public static void bigletters(Graphics2D g2d) {
        // Add row and column markers
        char rowMarker = (char) ('A' - 1); // Row letter

        for (int row = 0; row <= ROWS; row++) {
            char columnMarker = 'A'; // Column letter

            for (int column = 1; column <= COLUMNS; column += 2) {
                g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SQUARE_HEIGHT));
                g2d.setColor(MAIN_COLOR);

                double textX = column * SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2);
                double textY = row * SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2);

                g2d.drawString("" + rowMarker, (float) textX, (float) textY);
                g2d.drawString("" + columnMarker++, (float) (textX - SQUARE_WIDTH), (float) textY);
            }
            rowMarker++;
        }
    }
    
    public static void erase_cell(Graphics2D g2d, int column, int row) {
        double x = HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
        double y = HIGHLIGHT_ROW * SQUARE_HEIGHT;
        
        g2d.setColor(javax.swing.UIManager.getColor("Panel.background"));
        g2d.fill(new Rectangle2D.Double(x, y, RECTANGLE_WIDTH, SQUARE_HEIGHT));
        
        Mesh.small_grid(g2d, column, row);
    }
    
    static void grid(Graphics2D g2d) {
        // Draw vertical lines
        for (int column = 0; column <= COLUMNS * SUBCOLUMNS; column++) {
            double x = column * SUB_SQUARE_WIDTH;
            double y = 0;
            
        	Mesh.verticalLine(g2d, color(column % (SUBCOLUMNS * 2) == 0), SCREEN_HEIGHT, x, y);
        }

        // Draw horizontal lines
        for (int row = 0; row <= ROWS * SUBROWS; row++) {
            double x = 0;
            double y = row * SQUARE_HEIGHT;
            
        	Mesh.horizontalLine(g2d, MAIN_COLOR, SCREEN_WIDTH, x, y);
        }		
	}
    
    static void small_grid(Graphics2D g2d, int column, int row) {
        double x = HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
        double y = HIGHLIGHT_ROW * SQUARE_HEIGHT;
        
        // Draw vertical lines
        for (int cellcolumn = column * 2 * SUBCOLUMNS; cellcolumn <= (column + 1) *  2 * SUBCOLUMNS; cellcolumn++) {
        	Mesh.verticalLine(g2d, MAIN_COLOR, SQUARE_HEIGHT, x, y);
            x += SUB_SQUARE_WIDTH;
        }

        x = HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
        y = HIGHLIGHT_ROW * SQUARE_HEIGHT;
        
        // Draw horizontal lines
        for (int cellrow = row * SUBROWS; cellrow <= (row + 1) * SUBROWS; cellrow++) {
        	Mesh.horizontalLine(g2d, MAIN_COLOR, RECTANGLE_WIDTH, x, y);
        	y += SUB_SQUARE_HEIGHT;
        }
    }
    
    static void verticalLine(Graphics2D g2d, Color color, double length, double x, double y) {
        g2d.setColor(color);
        g2d.draw(new Line2D.Double(x, y, x, y + length));
    }
    
    static void horizontalLine(Graphics2D g2d, Color color, double length, double x, double y) {
        g2d.setColor(color);
        g2d.draw(new Line2D.Double(x, y, x + length, y));
    }
    
    private static Color color(boolean condition) {
    	return condition ? MAIN_COLOR : BACKGROUND_COLOR;
    }
}
