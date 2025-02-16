package navigation.ModeAdapters;

import javax.swing.JFrame;

import navigation.Mesh;
import navigation.Modes.Move;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static navigation.Parameters.*;

public class ClickModeAdapter extends KeyAdapter {
    private final JFrame frame;
    private final StringBuilder keySequence = new StringBuilder();

    public ClickModeAdapter(JFrame frame) {
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
                char target = keySequence.charAt(2);
                keySequence.setLength(0);
                frame.dispose();
                Move.move(target);
            }
        }
    }
}
