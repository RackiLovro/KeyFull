package src.main.java.com.keyfull;

import javax.swing.JFrame;

import src.main.java.com.keyfull.adapters.ClickMode;
import src.main.java.com.keyfull.adapters.DoubleClickMode;
import src.main.java.com.keyfull.adapters.DragMode;

import java.awt.event.KeyAdapter;

public class Mode {
    public static KeyAdapter getKeyAdapter(String mode, JFrame frame, Parameters params) {
        switch (mode.toLowerCase()) {
            case "drag":
                return new DragMode(frame, params);
            case "double_click":
                return new DoubleClickMode(frame, params);
            case "click":
                return new ClickMode(frame, params);
        }
		return null;
    }
}
