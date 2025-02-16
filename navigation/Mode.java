package navigation;

import javax.swing.JFrame;

import navigation.ModeAdapters.ClickModeAdapter;
import navigation.ModeAdapters.DragModeAdapter;

import java.awt.event.KeyAdapter;

public class Mode {
    public static KeyAdapter getKeyAdapter(String mode, JFrame frame) {
        switch (mode.toLowerCase()) {
            case "drag":
                return new DragModeAdapter(frame);
            case "click":
            default:
                return new ClickModeAdapter(frame);
        }
    }
}
