package navigation.ModeAdapters;

import javax.swing.JFrame;

import navigation.Mesh;
import navigation.Modes.Drag;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static navigation.Parameters.*;

public class DragModeAdapter extends KeyAdapter {
    private final JFrame frame;
    private final StringBuilder keySequence = new StringBuilder();
    private char start = '\0';

    public DragModeAdapter(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        char keyChar = evt.getKeyChar();

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if ((Character.isLetter(keyChar) || keyChar == ',') && keySequence.length() < 3) {
            keySequence.append(Character.toUpperCase(keyChar));

            if (keySequence.length() == 2) {
                HIGHLIGHT_ROW = keySequence.charAt(1) - 'A';
                HIGHLIGHT_COLUMN = keySequence.charAt(0) - 'A';
                Mesh.repaint_mesh();
            }

            if (keySequence.length() == 3) {
                if (start == '\0') {
                    start = keySequence.charAt(2);
                    keySequence.setLength(0);
                    HIGHLIGHT_ROW = -1;
                    HIGHLIGHT_COLUMN = -1;
                    Mesh.repaint_mesh();
                } else {
                    char end = keySequence.charAt(2);
                    keySequence.setLength(0);
                    frame.dispose();
                    Drag.select(start, end);
                }
            }
        }
    }
}
