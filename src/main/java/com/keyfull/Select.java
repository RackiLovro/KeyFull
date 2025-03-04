package com.keyfull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.keyfull.Parameters.*;

public class Select {
    private static JFrame[] frames;
    private static GraphicsDevice[] screens;
    private static boolean keyListenerActive = true;

    public static void main(String[] args) {
        initialize(); 
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (!keyListenerActive) return false;

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                handle_key_press(e, args);
                return true;
            }
            return false;
        });
    }
    
    private static void handle_key_press(KeyEvent e, String[] args) {
        int selectedScreen = e.getKeyCode() - KeyEvent.VK_1;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            SwingUtilities.invokeLater(() -> close_all_windows());
        } else if (selectedScreen >= 0 && selectedScreen < screens.length) {
            keyListenerActive = false;
            SwingUtilities.invokeLater(() -> close_all_windows());
            create_mesh(selectedScreen, args);
        }
    }

    private static void close_all_windows() {
        for (JFrame frame : frames) {
            if (frame != null && frame.isVisible()) {
                frame.dispose();
            }
        }
    }

    private static void initialize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screens = ge.getScreenDevices();
        frames = new JFrame[screens.length];

        ExecutorService executor = Executors.newFixedThreadPool(screens.length);

        for (int i = 0; i < screens.length; i++) {
            final int index = i;
            executor.execute(() -> {
                frames[index] = create_selection(screens[index], index);
            });
        }
        executor.shutdown();
    }

    private static BufferedImage load_selection_image(int screenIndex, int width, int height) {
        return Cache.loadOrCreateImage(CACHE_DIRECTORY, "select_" + screenIndex + ".png", width, height, (g2d, w, h) -> {
            String text = String.valueOf(screenIndex + 1);
            g2d.setFont(LABEL_FONT);
            FontMetrics metrics = g2d.getFontMetrics();
            
            int x = (w - metrics.stringWidth(text)) / 2;
            int y = (h - metrics.getHeight()) / 2 + metrics.getAscent();
            
            g2d.setColor(MAIN_COLOR);
            g2d.drawString(text, x, y);
        });
    }

    private static JFrame create_selection(GraphicsDevice screen, int screenIndex) {
        Rectangle bounds = screen.getDefaultConfiguration().getBounds();
        
        int width = bounds.width / 2;
        int height = bounds.height / 2;
        int x = bounds.x + (bounds.width - width) / 2;
        int y = bounds.y + (bounds.height - height) / 2;        
        
        BufferedImage selectionImage = load_selection_image(screenIndex, width, height);
        JFrame frame = new JFrame();

        JComponent backgroundComponent = new JComponent() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(selectionImage, 0, 0, null);
            }
        };
   
        configure_frame(frame);
        frame.setSize(width, height);
        frame.setLocation(x, y);
        frame.setContentPane(backgroundComponent);
        
        return frame;
    }

    private static void create_mesh(int selectedScreen, String[] args) {
        GraphicsDevice screen = screens[selectedScreen];
        GraphicsConfiguration gc = screen.getDefaultConfiguration();

        int screen_width = screen.getDisplayMode().getWidth();
        int screen_height = screen.getDisplayMode().getHeight();
        
        Parameters params = new Parameters(new Rectangle(0, 0, screen_width, screen_height));
        Rectangle bounds = gc.getBounds();
        JFrame frame = new JFrame(gc);
        
        new Mesh(frame, params);
        
        configure_frame(frame);
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        frame.add(Mesh.get_grid_panel());
        frame.addKeyListener(Mode.getKeyAdapter(args.length > 0 ? args[0] : "click", frame, params));
    }
    
    private static void configure_frame(JFrame frame) {
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
    }
}
