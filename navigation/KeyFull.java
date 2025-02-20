package navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import static navigation.Parameters.*;

public class KeyFull {
    private static JFrame[] frames;
    private static GraphicsDevice[] screens;
    private static boolean keyListenerActive = true;

    public static void main(String[] args) {
        initialize();

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (!keyListenerActive) return false;

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                handleKeyPress(e, args);
                return true;
            }
            return false;
        });
    }

    private static void initialize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screens = ge.getScreenDevices();
        frames = new JFrame[screens.length];

        for (int i = 0; i < screens.length; i++) {
            frames[i] = create_selection(screens[i], i);
        }
    }

    private static void handleKeyPress(KeyEvent e, String[] args) {
        int selectedScreen = e.getKeyCode() - KeyEvent.VK_1;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            close_all_windows();
        } else if (selectedScreen >= 0 && selectedScreen < screens.length) {
            keyListenerActive = false;
            close_all_windows();
            create_mesh(selectedScreen, args);
        }
    }

    private static JFrame create_selection(GraphicsDevice screen, int screenIndex) {
        JFrame frame = create_frame(screen, String.valueOf(screenIndex + 1));
        JLabel label = new JLabel(String.valueOf(screenIndex + 1), SwingConstants.CENTER);

        label.setFont(LABEL_FONT);
        label.setForeground(MAIN_COLOR);

        frame.add(label);
        return frame;
    }

    private static void create_mesh(int selectedScreen, String[] args) {
        JFrame frame = create_frame(screens[selectedScreen], "Keyfull");

        new Mesh(frame);
        frame.add(Mesh.get_grid_panel());
        frame.addKeyListener(Mode.getKeyAdapter(args.length > 0 ? args[0] : "click", frame));
    }

    private static void close_all_windows() {
        for (JFrame frame : frames) {
            frame.dispose();
        }
    }

    private static JFrame create_frame(GraphicsDevice screen, String title) {
        DisplayMode dm = screen.getDisplayMode();
        Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        JFrame frame = new JFrame(title);

        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(dm.getWidth(), dm.getHeight());
        frame.setLocation(bounds.x, bounds.y);

        return frame;
    }
}
