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
    static JPanel gridPanel;

    public Mesh(JFrame frame) {
        // Load or generate the grid image
    	BufferedImage cache = cache_grid();

        // Create a JPanel that will display the cached image
        gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the pre-rendered grid
                g2d.drawImage(cache, 0, 0, null);

                // Draw dynamic elements like highlights
                if (HIGHLIGHT_ROW >= 0 && HIGHLIGHT_COLUMN >= 0) {
                    Mesh.erase_cell(g2d);
                    Mesh.small_letters(g2d);
                }
            }
        };

        gridPanel.setOpaque(false);
    }
    
    public static void repaint_mesh() {
    	Mesh.gridPanel.repaint();
    }

    private static BufferedImage cache_grid() {
        File file = new File(GRID_IMAGE_PATH);
        
        if (file.exists() == true) {
            try {
                return ImageIO.read(file); // Load cached grid
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Generate and save the grid if no cache exists
        BufferedImage image = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(MAIN_COLOR);
        
        big_grid(g2d);
        big_letters(g2d);
        
        g2d.dispose();

        // Save the generated grid
        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return image;
    }

    private static void big_grid(Graphics2D g2d) {
        g2d.setColor(MAIN_COLOR);
        
        // Draw vertical lines
        for (double x = 0; x <= SCREEN_WIDTH; x += SQUARE_WIDTH * 2) {
            g2d.draw(new Line2D.Double(x, 0, x, SCREEN_HEIGHT));
        }

        // Draw horizontal lines
        for (double y = 0; y <= SCREEN_HEIGHT; y += SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(0, y, SCREEN_WIDTH, y));
        
        }
    }
    
    static void small_grid(Graphics2D g2d) {
        for (double x = highlight_width(); x <= highlight_width() + RECTANGLE_WIDTH; x += SUB_SQUARE_WIDTH) {
            g2d.draw(new Line2D.Double(x, highlight_height(), x, highlight_height() + SQUARE_HEIGHT));   	
        }       
        for (double y = highlight_height(); y <= highlight_height() + SQUARE_HEIGHT; y += SUB_SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(highlight_width(), y, highlight_width() + RECTANGLE_WIDTH, y));
        }
    }
    
    public static void erase_cell(Graphics2D g2d) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fill(new Rectangle2D.Double(highlight_width(), highlight_height(), RECTANGLE_WIDTH, SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);
        
        Mesh.small_grid(g2d);
    }
    
    private static void big_letters(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);
        
        // Add row and column markers
        char rowMarker = (char) ('A' - 1); // Row letter

        for (int row = 0; row <= ROWS; row++) {
            char columnMarker = 'A'; // Column letter

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
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) SUB_SQUARE_HEIGHT));
        
        float y = (float)(highlight_height() - SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95);
    	for(int subrow = 0; subrow < SUBROWS; subrow++) {
            double x = highlight_width();
    		for(int subcolumn = 0; subcolumn < SUBCOLUMNS * 2; subcolumn++) {
                g2d.drawString(String.valueOf(CELL.get(subrow).charAt(subcolumn)), (float) x, y);

                x += SUB_SQUARE_WIDTH;
    		}
    		y += SUB_SQUARE_HEIGHT;
    	}
    }
}
