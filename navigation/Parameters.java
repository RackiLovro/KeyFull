package navigation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

public class Parameters {
    // Grid dimensions
    public static final int COLUMNS = 52;
    public static final int ROWS = 26;

    public static final int SUBCOLUMNS = 4;
    public static final int SUBROWS = 3;

    public static int HIGHLIGHT_ROW = -1;
    public static int HIGHLIGHT_COLUMN = -1;

    // Colors
    public static final Color MAIN_COLOR = Color.GRAY;
    public static final Color SECONDARY_COLOR = Color.LIGHT_GRAY;
    public static final Color BACKGROUND_COLOR = javax.swing.UIManager.getColor("Panel.background");

    // Dynamically initialized dimensions
    public static double SQUARE_WIDTH;
    public static double SQUARE_HEIGHT;
    public static double RECTANGLE_WIDTH;

    public static double SUB_SQUARE_WIDTH;
    public static double SUB_SQUARE_HEIGHT;
  
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int SCREEN_WIDTH = screenSize.width;
    static int SCREEN_HEIGHT = screenSize.height;

    static {
        SQUARE_WIDTH = (double) screenSize.width / COLUMNS;
        RECTANGLE_WIDTH = 2 * SQUARE_WIDTH;
        SQUARE_HEIGHT = (double) screenSize.height / ROWS;
        
        SUB_SQUARE_WIDTH = (double) SQUARE_WIDTH / SUBCOLUMNS;
        SUB_SQUARE_HEIGHT = (double) SQUARE_HEIGHT / SUBROWS;
    }
    
    static List<String> CELL = List.of(
        "QWERTYUI",
        "ASDFGHJK",
        "ZXCVBNM,"
    );
}
