package com.keyfull.adapters;

import javax.swing.JFrame;
import com.keyfull.Mesh;
import com.keyfull.Parameters;
import com.keyfull.modes.Drag;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DragMode extends KeyAdapter {
    private final JFrame frame;
    private final StringBuilder keySequence = new StringBuilder();
    private final DragCoordinates dragCoordinates = new DragCoordinates();
    private Parameters params;

    public DragMode(JFrame frame, Parameters parameters) {
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
                if (dragCoordinates.isStartEmpty()) {
                    dragCoordinates.setStart(params.HIGHLIGHT_ROW, params.HIGHLIGHT_COLUMN, keySequence.charAt(2));
                    keySequence.setLength(0);
                    params.HIGHLIGHT_ROW = -1;
                    params.HIGHLIGHT_COLUMN = -1;
                    Mesh.repaint_mesh();
                } else {
                    dragCoordinates.setEnd(params.HIGHLIGHT_ROW, params.HIGHLIGHT_COLUMN, keySequence.charAt(2));
                    keySequence.setLength(0);      
                    frame.dispose();
                    Drag.drag(dragCoordinates, params);
                }
            }
        }
    }
}