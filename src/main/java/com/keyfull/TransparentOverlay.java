package com.keyfull;

import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JFrame;
import com.sun.jna.Pointer;
import com.keyfull.AppKit;

public class TransparentOverlay {

    private static void createMesh(int selectedScreen, String[] args) {
        GraphicsDevice[] screens = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        GraphicsDevice screen = screens[selectedScreen];
        GraphicsConfiguration gc = screen.getDefaultConfiguration();

        Rectangle bounds = gc.getBounds();
        JFrame frame = new JFrame(gc);
        
        configureFrame(frame);
        frame.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        frame.setVisible(true);

        // After the frame is visible, retrieve the native NSWindow pointer and adjust its level
        setWindowAboveMenuBar(frame);
    }

    private static void configureFrame(JFrame frame) {
        frame.setUndecorated(true);
        frame.setOpacity(0.5f);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
    }

    private static void setWindowAboveMenuBar(JFrame frame) {
        try {
            // Ensure the native peer is created.
            if (!frame.isDisplayable()) {
                frame.addNotify();
            }
            // Instead of calling getPeer(), retrieve the private "peer" field.
            Field peerField = Component.class.getDeclaredField("peer");
            peerField.setAccessible(true);
            Object peer = peerField.get(frame);
            
            // Now use reflection to call the private "getNSWindow" method on the peer.
            Method getNSWindowMethod = peer.getClass().getDeclaredMethod("getNSWindow");
            getNSWindowMethod.setAccessible(true);
            long nsWindowPtr = (Long) getNSWindowMethod.invoke(peer);
            Pointer nsWindow = new Pointer(nsWindowPtr);

            // Register the selector for "setLevel:".
            Pointer selSetLevel = AppKit.INSTANCE.sel_registerName("setLevel:");

            // Adjust the window level to NSStatusWindowLevel so it sits above the menu bar.
            AppKit.INSTANCE.objc_msgSend(nsWindow, selSetLevel, AppKit.NSStatusWindowLevel);

            System.out.println("Window level adjusted to be above the menu bar.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to adjust the NSWindow level via peer not get peer.");
        }
    }


    public static void main(String[] args) {
        createMesh(0, args);
    }
}
