package com.keyfull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import static com.keyfull.Parameters.*;

public class Mesh {
    private static JPanel gridPanel;
    private static Parameters params;

    public static JPanel get_grid_panel() {
    	return Mesh.gridPanel;
    }
    
    public static void repaint_mesh() {
    	gridPanel.repaint();
    }

    public Mesh(JFrame frame, Parameters parameters) {
    	this.params = parameters;
    	BufferedImage cache = cache_grid();

        gridPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(cache, 0, 0, null);

                if (params.HIGHLIGHT_ROW >= 0 && params.HIGHLIGHT_COLUMN >= 0) {
                    Mesh.erase_cell(g2d);
                    Mesh.small_grid(g2d);
                    Mesh.small_letters(g2d);
                }
            }
        };

        gridPanel.setOpaque(false);
    }

    private static BufferedImage cache_grid() {
        return Cache.loadOrCreateImage(CACHE_DIRECTORY, File.separator + params.SCREEN_WIDTH + "_" + params.SCREEN_HEIGHT + ".png", params.SCREEN_WIDTH, params.SCREEN_HEIGHT, (g2d, w, h) -> {
            g2d.setColor(MAIN_COLOR);
            big_grid(g2d);
            big_letters(g2d);
        });
    }

    private static void big_grid(Graphics2D g2d) {
        for (double x = 0; x <= params.SCREEN_WIDTH; x += params.SQUARE_WIDTH * 2) {
            g2d.draw(new Line2D.Double(x, 0, x, params.SCREEN_HEIGHT));
        }
        for (double y = 0; y <= params.SCREEN_HEIGHT; y += params.SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(0, y, params.SCREEN_WIDTH, y));       
        }
    }
    
    static void small_grid(Graphics2D g2d) {
        for (double x = params.highlight_width(); x <= params.highlight_width() + params.RECTANGLE_WIDTH; x += params.SUB_SQUARE_WIDTH) {
            g2d.draw(new Line2D.Double(x, params.highlight_height(), x, params.highlight_height() + params.SQUARE_HEIGHT));   	
        }       
        for (double y = params.highlight_height(); y <= params.highlight_height() + params.SQUARE_HEIGHT; y += params.SUB_SQUARE_HEIGHT) {
            g2d.draw(new Line2D.Double(params.highlight_width(), y, params.highlight_width() + params.RECTANGLE_WIDTH, y));
        }
    }
    
    public static void erase_cell(Graphics2D g2d) {
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fill(new Rectangle2D.Double(params.highlight_width(), params.highlight_height(), params.RECTANGLE_WIDTH, params.SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);
    }
    
    private static void big_letters(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) params.SQUARE_HEIGHT));
        g2d.setColor(MAIN_COLOR);
        
        char row_letter = (char) ('A' - 1);

        for (int row = 0; row <= ROWS; row++) {
            char column_letter = 'A';

            for (int column = 1; column <= COLUMNS; column += 2) {

                double x = column * params.SQUARE_WIDTH + params.SQUARE_WIDTH / (SUBCOLUMNS * 2);
                double y = row * params.SQUARE_HEIGHT - params.SQUARE_HEIGHT / (SUBROWS * 2);

                g2d.drawString(String.valueOf(row_letter), (float) x, (float) y);
                g2d.drawString(String.valueOf(column_letter++), (float) (x - params.SQUARE_WIDTH), (float) y);
            }
            row_letter++;
        }
    }
    
	public static void small_letters(Graphics2D g2d) {
        g2d.setFont(new Font("Monospaced", Font.PLAIN, (int) params.SUB_SQUARE_HEIGHT));
        
        float y = (float)(params.highlight_height() - params.SUB_SQUARE_HEIGHT * 2 + params.SQUARE_HEIGHT * 0.95);
    	for(int subrow = 0; subrow < SUBROWS; subrow++) {
            double x = params.highlight_width();
    		for(int subcolumn = 0; subcolumn < SUBCOLUMNS * 2; subcolumn++) {
                g2d.drawString(String.valueOf(CELL.get(subrow).charAt(subcolumn)), (float) x, y);

                x += params.SUB_SQUARE_WIDTH;
    		}
    		y += params.SUB_SQUARE_HEIGHT;
    	}
    }
}
