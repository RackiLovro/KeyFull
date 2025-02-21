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
            //Rectangle bounds = screens[i].getDefaultConfiguration().getBounds();
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
        Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        int width = bounds.width / 2;
        int height = bounds.height / 2;
        int x = bounds.x + (bounds.width - width) / 2;
        int y = bounds.y + (bounds.height - height) / 2;

        JFrame frame = new JFrame(String.valueOf(screenIndex + 1));
        
        frame.setUndecorated(true);
        frame.setSize(width, height);
        frame.setLocation(x, y);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel(String.valueOf(screenIndex + 1), SwingConstants.CENTER);
        
        label.setFont(LABEL_FONT);
        label.setForeground(MAIN_COLOR);
        frame.add(label);
        frame.setVisible(true);
        return frame;
    }

    private static void create_mesh(int selectedScreen, String[] args) {
        GraphicsDevice screen = screens[selectedScreen];
        GraphicsConfiguration gc = screen.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();

        JFrame frame = new JFrame(gc);
        frame.setTitle("Keyfull");
        frame.setUndecorated(true); 
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height - 35);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        new Mesh(frame);
        
        frame.add(Mesh.get_grid_panel());
        frame.addKeyListener(Mode.getKeyAdapter(args.length > 0 ? args[0] : "click", frame));
        frame.setVisible(true);
    }

    private static void close_all_windows() {
        for (JFrame frame : frames) {
            if (frame != null) {
                frame.dispose();
            }
        }
    }
}
