package navigation.Adapters;

import javax.swing.JFrame;
import navigation.Mesh;
import navigation.Parameters;
import navigation.Modes.Click;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClickMode extends KeyAdapter {
    private final JFrame frame;
    private final StringBuilder keySequence = new StringBuilder();
    private Parameters params;

    public ClickMode(JFrame frame, Parameters parameters) {
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
                int frameX = frame.getX();  // Capture frame's X offset
                int frameY = frame.getY();  // Capture frame's Y offset
                frame.dispose();
                Click.click(target, frameX, frameY, this.params);
            }
        }
    }
}
