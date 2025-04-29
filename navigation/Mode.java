package navigation;

import javax.swing.JFrame;

import navigation.Adapters.ClickMode;
import navigation.Adapters.DoubleClickMode;
import navigation.Adapters.DragMode;

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
