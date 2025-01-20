package hello;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class backup {
    static int columns = 52;
    static int rows = 26;
    

    static int subcolumns = 4;
    static int subrows = 3;
    
    static String[][] cell ={
            {"q", "w", "e", "r", "t", "y", "u", "u"},
            {"a", "s", "d", "f", "g", "h", "j", "k"},
            {"z", "x", "c", "v", "b", "n", "m", ","}
        };

    // Get screen dimensions
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Calculate the square size as floating-point values
    static double squareWidth;
    static double squareHeight;
    
    static Graphics2D g2d;

    // To track key sequence
    static StringBuilder keySequence = new StringBuilder();
    

    private static void highlight(char row, char column) {
    	double x = (column - 97) * squareWidth;
    	double y = (row - 97) * squareWidth;
    	
    	System.out.println(x);
    	System.out.println(y);
    	
        //g2d.setColor(Color.GREEN);
        
        //Rectangle2D.Double rectangle
    	for(int subrow = 0; subrow <= subrows; subrow++) {
    		for(int subcolumn = 0; subcolumn <= subcolumns; subcolumn++) {
                g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) squareHeight));
                g2d.setColor(Color.GRAY);
                
                //g2d.drawString(cell[subrow][subcolumn], (float) x, (float) y);
                g2d.drawString("a", (float) x, (float) y);
                g2d.drawRect(10, 10, 700, 700);
                x += squareWidth;
    		}
    		y += squareHeight;
    	}
    }
    
	public static void drawLine(int column, int row) {
        double x = column * squareWidth / subcolumns;
        double y = row * squareHeight / subrows;
        
        //Color color = (column + row * 2) % (subcolumns * 2) == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY;
        
        if(column == 0) {
            Color color = (row * 2) % (subrows) == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY;
            g2d.setColor(color);
        }
        else {
            Color color = (column) % (subcolumns * 2) == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY;
            g2d.setColor(color);
        }

        Line2D.Double line = new Line2D.Double(x, y, row == 0 ? x : screenSize.width, column == 0 ? y : screenSize.height);
        g2d.draw(line);
	}

    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Transparent Full-Screen Window with Square Grid");

        // Remove window decorations (title bar, borders)
        frame.setUndecorated(true);

        // Set the window to be transparent
        frame.setOpacity(0.5f); // 50% transparency

        // Make the window close when pressing "ESC"
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                // Close on ESC
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }

                char keyChar = evt.getKeyChar();
                // Check for valid key presses (letters or digits)
                if (Character.isLetter(keyChar)) {
                    keySequence.append(keyChar);

                    // If the sequence reaches 3 characters, process it
                    if (keySequence.length() == 2) {
                    	System.out.println(keySequence);
                    	//HelloWorld.highlight(keySequence.charAt(0), keySequence.charAt(1));
                    	backup.highlight('g', 'h');

                        // Clear the sequence for the next input
                        keySequence.setLength(0);
                    }
                }
            }
        });

        // Create a custom JPanel for the grid
        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                squareWidth = (double) screenSize.width / columns;
                squareHeight = (double) screenSize.height / rows;

                // Draw vertical lines
                for (int column = 0; column <= columns * subcolumns; column++) {
                	backup.drawLine(column, 0);
                }

                // Draw horizontal lines
                for (int row = 0; row <= rows * subrows; row++) {
                	backup.drawLine(0, row);
                }

                // Add row and column markers
                char rowMarker = (char) ('A' - 1); // Row letter

                for (int row = 0; row <= rows; row++) {
                    char columnMarker = 'A'; // Column letter

                    for (int column = 0; column <= columns; column++) {
                        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) squareHeight));

                        g2d.setColor(Color.GRAY);

                        double textX = column * squareWidth + squareWidth / (subcolumns * 2);
                        double textY = row * squareHeight - squareHeight / (subrows * 2);

                        if (column % 2 != 0) {
                            g2d.drawString("" + rowMarker, (float) textX, (float) textY);
                        } else {
                            g2d.drawString("" + columnMarker++, (float) textX, (float) textY);
                        }
                    }
                    rowMarker++;
                }
            }
        };

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
}
