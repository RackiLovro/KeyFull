package navigation;

import javax.swing.JFrame;

public class KeyFull {
    public static void main(String[] args) {
        //String mode = (args.length > 0) ? args[0] : "click"; // Default to "click"
        JFrame frame = new JFrame("Transparent Full-Screen Window");
        Mesh mesh = new Mesh(frame);
        frame.add(mesh.gridPanel);
        frame.setUndecorated(true);
        frame.setOpacity(0.5f);
        frame.setSize(Parameters.SCREEN_WIDTH, Parameters.SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addKeyListener(Mode.getKeyAdapter("click", frame));
    }

    public static void run(String mode) {
        main(new String[]{mode}); // Allow re-running
    }
}
