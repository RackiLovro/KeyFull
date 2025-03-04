package src.main.java.com.keyfull;

import java.awt.*;
import java.util.List;

public class Parameters {
    public static final List<String> CELL = List.of(
            "QWERTYUI",
            "ASDFGHJK",
            "ZXCVBNM,"
    );

    static final String CACHE_DIRECTORY = "./cache";

    public static final int COLUMNS = 52;
    public static final int ROWS = 26;
    public static final int SUBCOLUMNS = 4;
    public static final int SUBROWS = 3;

    public static final Color MAIN_COLOR = Color.GRAY;
    public static final Color SECONDARY_COLOR = Color.LIGHT_GRAY;
    public static final Color BACKGROUND_COLOR = javax.swing.UIManager.getColor("Panel.background");
    
    public static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 100);

    public final double SQUARE_HEIGHT;
    public final double SUB_SQUARE_HEIGHT;
    public final double SQUARE_WIDTH;
    public final double RECTANGLE_WIDTH;
    public final double SUB_SQUARE_WIDTH;
    public final double WIDTH_OFFSET;
    public final double HEIGHT_OFFSET;
    public final float BIG_LETTER_OFFSET_WIDTH;
    public final float BIG_LETTER_OFFSET_HEIGHT;

    public final int SCREEN_WIDTH;
    public final int SCREEN_HEIGHT;
    
    public int HIGHLIGHT_ROW = -1;
    public int HIGHLIGHT_COLUMN = -1;

    public Parameters(Rectangle screenBounds) {
        this.SCREEN_WIDTH = screenBounds.width;
        this.SCREEN_HEIGHT = screenBounds.height;

        this.SQUARE_HEIGHT = (double) SCREEN_HEIGHT / ROWS;
        this.SUB_SQUARE_HEIGHT = (double) SQUARE_HEIGHT / SUBROWS;
        this.SQUARE_WIDTH = (double) SCREEN_WIDTH / COLUMNS;
        this.RECTANGLE_WIDTH = 2 * SQUARE_WIDTH;
        this.SUB_SQUARE_WIDTH = (double) SQUARE_WIDTH / SUBCOLUMNS;
        this.WIDTH_OFFSET = SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2);
        this.HEIGHT_OFFSET = SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2);
        this.BIG_LETTER_OFFSET_WIDTH = (float) (SQUARE_WIDTH + SQUARE_WIDTH / (SUBCOLUMNS * 2));
        this.BIG_LETTER_OFFSET_HEIGHT = (float) (SQUARE_HEIGHT - SQUARE_HEIGHT / (SUBROWS * 2));
    }

    public double highlight_width() {
        return HIGHLIGHT_COLUMN * RECTANGLE_WIDTH;
    }

    public double highlight_height() {
        return HIGHLIGHT_ROW * SQUARE_HEIGHT;
    }
}
