package navigation;

import static navigation.Parameters.*;

import javax.swing.JFrame;

public class KeyFull {
    public static void main(String[] args) {
        StringBuilder keySequence = new StringBuilder();       
        // Create a JFrame
    	JFrame frame = new JFrame("Transparent Full-Screen Window with Square Grid");
    	Mesh mesh = new Mesh(frame);

        // Add the grid panel to the frame
        frame.add(mesh.gridPanel);

        // Remove window decorations (title bar, borders)
        frame.setUndecorated(true);

        // Set the window to be transparent
        frame.setOpacity(0.5f); // 50% transparency

        // Set frame size explicitly to match grid dimensions
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);

        // Set default close operation to dispose of the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
        // Add key listener to track input and highlight cells
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                char keyChar = evt.getKeyChar();
            	
                // Close on ESC
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                else if ((Character.isLetter(keyChar) || keyChar == ',') && keySequence.length() < 3) {
                    keySequence.append(Character.toUpperCase(keyChar) );

                    // If the sequence reaches 2 characters, process it
                    if (keySequence.length() == 2) {
                        // Convert to grid indices
                        HIGHLIGHT_ROW = keySequence.charAt(1) - 'A';
                        HIGHLIGHT_COLUMN = keySequence.charAt(0) - 'A';
                        
                        // Repaint the panel to show the highlight
                        mesh.gridPanel.repaint();
                    }
                    
                    if (keySequence.length() == 3) {
                        Move.move(keySequence.charAt(2));
                        
                        keySequence.setLength(0);
                        System.exit(0);
                    }
                }
            }
        });
    }
}
