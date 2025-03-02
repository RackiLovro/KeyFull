package navigation.Adapters;

import javax.swing.JFrame;

import navigation.Mesh;
import navigation.Parameters;
import navigation.Modes.DoubleClick;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DoubleClickMode extends KeyAdapter {
    private final JFrame frame;
    private final StringBuilder keySequence = new StringBuilder();
    private Parameters params;

    public DoubleClickMode(JFrame frame, Parameters parameters) {
        this.frame = frame;
        this.params = parameters;
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        char keyChar = evt.getKeyChar();

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if ((Character.isLetter(keyChar) || keyChar == ',') && keySequence.length() < 3) {
            keySequence.append(Character.toUpperCase(keyChar));

            if (keySequence.length() == 2) {
                params.HIGHLIGHT_ROW = keySequence.charAt(1) - 'A';
                params.HIGHLIGHT_COLUMN = keySequence.charAt(0) - 'A';
                Mesh.repaint_mesh();
            }

            if (keySequence.length() == 3) {
                char target = keySequence.charAt(2);
                keySequence.setLength(0);
                frame.dispose();
                DoubleClick.double_click(target, params);
            }
        }
    }
}
