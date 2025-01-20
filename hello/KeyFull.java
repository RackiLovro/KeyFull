package hello;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class KeyFull {
    
    static int columns = 52;
    static int rows = 26;

    static int subcolumns = 4;
    static int subrows = 3;

    static int highlightRow = -1;
    static int highlightColumn = -1;
    
    static double squareWidth;
    static double squareHeight;
    static double rectangleWidth;
    
    static double subSquareWidth;
    static double subSquareHeight;
    
    static Color maincolor = Color.GRAY;
    static Color secondarycolor = Color.LIGHT_GRAY;

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    static StringBuilder keySequence = new StringBuilder();
    
    static List<String> cell = List.of(
        "QWERTYUI",
        "ASDFGHJK",
        "ZXCVBNM,"
    );


    public static void main(String[] args) {
        squareWidth = (double) screenSize.width / columns;
        rectangleWidth = 2 * squareWidth;
        squareHeight = (double) screenSize.height / rows;
        
        subSquareWidth = (double) squareWidth / subcolumns;
        subSquareHeight = (double) squareHeight / subrows;
        
        // Create a JFrame
        JFrame frame = new JFrame("Transparent Full-Screen Window with Square Grid");

        // Remove window decorations (title bar, borders)
        frame.setUndecorated(true);

        // Set the window to be transparent
        frame.setOpacity(0.5f); // 50% transparency

        // Create a custom JPanel for the grid
        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                KeyFull.grid(g2d);
                KeyFull.bigletters(g2d);
                
                // Highlight a rectangle if specified
                if (highlightRow >= 0 && highlightColumn >= 0) {
                	KeyFull.eraseCell(g2d, highlightRow, highlightColumn);
                	KeyFull.smallletters(g2d);
                }
            }
        };

        // Add key listener to track input and highlight cells
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {

                char keyChar = evt.getKeyChar();
            	
                // Close on ESC
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                else if (Character.isLetter(keyChar) && keySequence.length() < 3) {
                    keySequence.append(Character.toUpperCase(keyChar) );

                    // If the sequence reaches 2 characters, process it
                    if (keySequence.length() == 2) {
                        // Convert to grid indices
                        highlightRow = keySequence.charAt(1) - 'A';
                        highlightColumn = keySequence.charAt(0) - 'A';
                        
                        // Repaint the panel to show the highlight
                        gridPanel.repaint();
                    }
                    
                    if (keySequence.length() == 3) {
                        KeyFull.move(keySequence.charAt(2));
                        
                        keySequence.setLength(0);
                        System.exit(0);
                    }
                }
            }
        });

        // Set grid panel to be transparent
        gridPanel.setOpaque(false);

        // Add the grid panel to the frame
        frame.add(gridPanel);

        // Set frame size explicitly to match grid dimensions
        frame.setSize(screenSize.width, screenSize.height);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

        // Set default close operation to dispose of the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

	protected static void move(char target) {
        try {
	        double x = highlightColumn * rectangleWidth;
	        double y = highlightRow * squareHeight - subSquareHeight * 2 + squareHeight * 0.95;
	        
    		for (String row : cell) {
    			if (row.indexOf(target) != -1) {
    		       x += row.indexOf(target) * subSquareWidth;
    		       y += cell.indexOf(row) * subSquareHeight;
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

	private static void smallletters(Graphics2D g2d) {
        double y = highlightRow * squareHeight - subSquareHeight * 2 + squareHeight * 0.95;

    	for(int subrow = 0; subrow < subrows; subrow++) {
            double x = highlightColumn * rectangleWidth;
            
    		for(int subcolumn = 0; subcolumn < subcolumns * 2; subcolumn++) {
                g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) subSquareHeight));
                g2d.setColor(KeyFull.maincolor);
                g2d.drawString(String.valueOf(cell.get(subrow).charAt(subcolumn)), (float) x, (float) y);

                x += subSquareWidth;
    		}
    		y += subSquareHeight;
    	}
    }

	private static void bigletters(Graphics2D g2d) {
        // Add row and column markers
        char rowMarker = (char) ('A' - 1); // Row letter

        for (int row = 0; row <= rows; row++) {
            char columnMarker = 'A'; // Column letter

            for (int column = 1; column <= columns; column += 2) {
                g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) squareHeight));
                g2d.setColor(KeyFull.maincolor);

                double textX = column * squareWidth + squareWidth / (subcolumns * 2);
                double textY = row * squareHeight - squareHeight / (subrows * 2);

                g2d.drawString("" + rowMarker, (float) textX, (float) textY);
                g2d.drawString("" + columnMarker++, (float) (textX - squareWidth), (float) textY);
            }
            rowMarker++;
        }
    }
    
    private static void eraseCell(Graphics2D g2d, int column, int row) {
        double x = highlightColumn * rectangleWidth;
        double y = highlightRow * squareHeight;
        
        g2d.setColor(javax.swing.UIManager.getColor("Panel.background"));
        g2d.fill(new Rectangle2D.Double(x, y, rectangleWidth, squareHeight));
        
        KeyFull.grid(g2d, column, row);
    }
    
    protected static void grid(Graphics2D g2d) {
        // Draw vertical lines
        for (int column = 0; column <= columns * subcolumns; column++) {
            double x = column * subSquareWidth;
            double y = 0;
            
        	KeyFull.verticalLine(g2d, color(column % (subcolumns * 2) == 0), screenSize.height, x, y);
        }

        // Draw horizontal lines
        for (int row = 0; row <= rows * subrows; row++) {
            double x = 0;
            double y = row * subSquareHeight;
            
        	KeyFull.horizontalLine(g2d, color(row % subrows == 0), screenSize.width, x, y);
        }
		
	}
    
    private static void grid(Graphics2D g2d, int column, int row) {
        double x = highlightColumn * rectangleWidth;
        double y = highlightRow * squareHeight;
        
        // Draw vertical lines
        for (int cellcolumn = column * 2 * subcolumns; cellcolumn <= (column + 1) *  2 * subcolumns; cellcolumn++) {
        	KeyFull.verticalLine(g2d, KeyFull.maincolor, squareHeight, x, y);
            x += subSquareWidth;
        }

        x = highlightColumn * rectangleWidth;
        y = highlightRow * squareHeight;
        
        // Draw horizontal lines
        for (int cellrow = row * subrows; cellrow <= (row + 1) * subrows; cellrow++) {
        	KeyFull.horizontalLine(g2d, KeyFull.maincolor, rectangleWidth, x, y);
        	y += subSquareHeight;
        }
    }
    
    private static void verticalLine(Graphics2D g2d, Color color, double length, double x, double y) {
        g2d.setColor(color);
        g2d.draw(new Line2D.Double(x, y, x, y + length));
    }
    
    private static void horizontalLine(Graphics2D g2d, Color color, double length, double x, double y) {
        g2d.setColor(color);
        g2d.draw(new Line2D.Double(x, y, x + length, y));
    }
    
    private static Color color(boolean condition) {
    	return condition ? KeyFull.maincolor : KeyFull.secondarycolor;
    }
}
