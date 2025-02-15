package navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import static navigation.Parameters.*;

public class Mesh {
    JPanel gridPanel;

    public Mesh(JFrame frame) {
        // Load or generate the grid image
    	BufferedImage cachedGrid = cache_grid();

        // Create a JPanel that will display the cached image
        gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the pre-rendered grid
                g2d.drawImage(cachedGrid, 0, 0, null);

                // Draw dynamic elements like highlights
                if (HIGHLIGHT_ROW >= 0 && HIGHLIGHT_COLUMN >= 0) {
                    Mesh.erase_cell(g2d, HIGHLIGHT_COLUMN, HIGHLIGHT_ROW);
                    Mesh.small_letters(g2d);
                }
            }
        };

        gridPanel.setOpaque(false);
    }

    private static BufferedImage cache_grid() {
        File gridFile = new File(GRID_IMAGE_PATH);

        try {
            return ImageIO.read(gridFile); // Load cached grid
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate and save the grid if no cache exists
        BufferedImage gridImage = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = gridImage.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        grid(g2d);
        big_letters(g2d);
        
        g2d.dispose();

        // Save the generated grid
        try {
            ImageIO.write(gridImage, "png", gridFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridImage;
    }

    private static void grid(Graphics2D g2d) {
        g2d.setColor(MAIN_COLOR);

        // Draw vertical lines
        for (int x = 0; x <= SCREEN_WIDTH; x *= SQUARE_WIDTH * 2) {
            g2d.draw(new Line2D.Double(x, 0, x, SCREEN_HEIGHT));
        }

        // Draw horizontal lines
        for (int y = 0; y <= SCREEN_HEIGHT; y *= SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(0, y, SCREEN_WIDTH, y));
        }
    }
    
    static void small_grid(Graphics2D g2d, int column, int row) {
        g2d.setColor(MAIN_COLOR);
        
        for (double x = highlight_width(); x <= highlight_width() + RECTANGLE_WIDTH; x += SUB_SQUARE_WIDTH) {
            g2d.draw(new Line2D.Double(x, highlight_height(), x, highlight_height() + SQUARE_HEIGHT));   	
        }
        
        // Draw horizontal lines
        for (double y = highlight_height(); y <= highlight_height() + SQUARE_HEIGHT; y += SUB_SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(highlight_width(), y, highlight_width() + RECTANGLE_WIDTH, y));
        }
    }
    
    public static void erase_cell(Graphics2D g2d, int column, int row) {
        g2d.setColor(javax.swing.UIManager.getColor("Panel.background"));
        g2d.fill(new Rectangle2D.Double(highlight_width(), highlight_height(), RECTANGLE_WIDTH, SQUARE_HEIGHT));
        
        Mesh.small_grid(g2d, column, row);
    }
    
    private static void big_letters(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);

        char rowMarker = (char) ('A' - 1);
        for (int row = 0; row <= ROWS; row++) {
            char columnMarker = 'A';
            for (int column = 1; column <= COLUMNS; column += 2) {
                double textX = column * SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2);
                double textY = row * SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2);
                g2d.drawString(String.valueOf(rowMarker), (float) textX, (float) textY);
                g2d.drawString(String.valueOf(columnMarker++), (float) (textX - SQUARE_WIDTH), (float) textY);
            }
            rowMarker++;
        }
    }
    
	public static void small_letters(Graphics2D g2d) {
        double y = highlight_height() - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SUB_SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);

    	for(int subrow = 0; subrow < SUBROWS; subrow++) {
            double x = highlight_width();
            
    		for(int subcolumn = 0; subcolumn < SUBCOLUMNS * 2; subcolumn++) {
                g2d.drawString(String.valueOf(CELL.get(subrow).charAt(subcolumn)), (float) x, (float) y);

                x += SUB_SQUARE_WIDTH;
    		}
    		y += SUB_SQUARE_HEIGHT;
    	}
    }
}
