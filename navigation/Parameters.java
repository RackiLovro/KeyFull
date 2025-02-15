package navigation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

public class Parameters {
    public static final List<String> CELL = List.of(
            "QWERTYUI",
            "ASDFGHJK",
            "ZXCVBNM,"
        );

    static final String GRID_IMAGE_PATH = "grid_cache.png";
    // Grid dimensions
    public static final int COLUMNS = 52;
    public static final int ROWS = 26;

    public static final int SUBCOLUMNS = 4;
    public static final int SUBROWS = 3;

    // Colors
    public static final Color MAIN_COLOR = Color.GRAY;
    public static final Color SECONDARY_COLOR = Color.LIGHT_GRAY;
    public static final Color BACKGROUND_COLOR = javax.swing.UIManager.getColor("Panel.background");
  
    public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = screenSize.width;
    public static final int SCREEN_HEIGHT = screenSize.height;


    public static final double SQUARE_HEIGHT = (double) screenSize.height / ROWS;
    public static final double SUB_SQUARE_HEIGHT = (double) SQUARE_HEIGHT / SUBROWS;
    
    public static final double SQUARE_WIDTH = (double) screenSize.width / COLUMNS;
    public static final double RECTANGLE_WIDTH = 2 * SQUARE_WIDTH;
    public static final double SUB_SQUARE_WIDTH = (double) SQUARE_WIDTH / SUBCOLUMNS;
    
    public static final double WIDTH_OFFSET = SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2);
    public static final double HEIGHT_OFFSET = SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2);
    
    public static final float BIG_LETTER_OFFSET_WIDTH = (float) (SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2));
    public static final float BIG_LETTER_OFFSET_HEIGHT = (float) (SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2));
    //public static final double SMALL_LETTER_OFFSET_HEIGHT = SUB_SQUARE_HEIGHT * 2 + SQUARE_HEIGHT * 0.95;

    public static int HIGHLIGHT_ROW = -1;
    public static int HIGHLIGHT_COLUMN = -1;
    
    public static double highlight_width() { return HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;}
    public static double highlight_height() { return HIGHLIGHT_ROW * SQUARE_HEIGHT;}
    
}
