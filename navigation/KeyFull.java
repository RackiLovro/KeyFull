package navigation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService executor = Executors.newFixedThreadPool(screens.length);

        for (int i = 0; i < screens.length; i++) {
            final int index = i; // Capture variable for lambda
            executor.execute(() -> {
                frames[index] = create_selection(screens[index], index);
            });
        }
        executor.shutdown(); // Prevent further task submission
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

    private static BufferedImage loadSelectionImage(int screenIndex, int width, int height) {
        String selectionImagePath = "select_cache_" + screenIndex + ".png";
        File file = new File(selectionImagePath);

        if (file.exists()) {
            try {
                return ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(MAIN_COLOR);
        g2d.setFont(LABEL_FONT);
        g2d.drawString(String.valueOf(screenIndex + 1), width / 2, height / 2);
        g2d.dispose();

        try {
            ImageIO.write(image, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    private static JFrame create_selection(GraphicsDevice screen, int screenIndex) {
        Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        int width = bounds.width / 2;  // Example width
        int height = bounds.height / 2;  // Example height

        int x = bounds.x + (bounds.width - width) / 2;
        int y = bounds.y + (bounds.height - height) / 2;        
        
        BufferedImage selectionImage = loadSelectionImage(screenIndex, width, height);

        JComponent backgroundComponent = new JComponent() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Ensure the background is cleared
                g.drawImage(selectionImage, 0, 0, null);  // Draw the image as background
            }
        };

        JFrame frame = new JFrame();
        
        frame.setUndecorated(true);  // Remove window decoration (border, title bar)
        frame.setSize(width, height);  // Set the frame size
        frame.setLocation(x, y);  // Set the frame location on the screen
        frame.setOpacity(0.5f);  // Set frame opacity
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Ensure the frame closes properly
        frame.setContentPane(backgroundComponent);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
        
        return frame;
    }

    private static void create_mesh(int selectedScreen, String[] args) {
        GraphicsDevice screen = screens[selectedScreen];
        GraphicsConfiguration gc = screen.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();

        // Create Parameters instance for selected screen
        Parameters params = new Parameters(bounds);

        JFrame frame = new JFrame(gc);
        frame.setTitle("Keyfull");
        frame.setUndecorated(true);
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height - 35);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        new Mesh(frame, params); // Pass parameters to Mesh

        frame.add(Mesh.get_grid_panel());
        frame.addKeyListener(Mode.getKeyAdapter(args.length > 0 ? args[0] : "click", frame, params));
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
