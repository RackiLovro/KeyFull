package com.keyfull;

import com.sun.jna.Native;
import com.sun.jna.platform.unix.X11;
import javax.swing.JFrame;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.sun.jna.Memory;

public class X11OverlayUtil {

    public static long getX11WindowID(JFrame frame) {
        try {
            // Ensure the frame is realized so that its peer exists.
            frame.addNotify();
            // Access the "peer" field of Component using reflection.
            Field peerField = Component.class.getDeclaredField("peer");
            peerField.setAccessible(true);
            Object peer = peerField.get(frame);
            if (peer == null) {
                throw new RuntimeException("Peer is null; ensure the frame is realized.");
            }
            // Use reflection to call the non-public "getWindow" method on the peer.
            Method getWindowMethod = peer.getClass().getDeclaredMethod("getWindow");
            getWindowMethod.setAccessible(true);
            Object window = getWindowMethod.invoke(peer);
            if (window instanceof Number) {
                return ((Number) window).longValue();
            } else {
                throw new RuntimeException("Unexpected type for window ID: " + window.getClass());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to obtain X11 window ID", e);
        }
    }

    public static void setWindowOverlay(JFrame frame) {
        // Ensure frame is visible so that a native window is created.
        frame.addNotify();
        long windowId = getX11WindowID(frame);

        X11 x11 = X11.INSTANCE;
        X11.Display display = x11.XOpenDisplay(null);
        if (display == null) {
            System.err.println("Cannot open X display");
            return;
        }

        X11.Window xWindow = new X11.Window(windowId);

        // Set the window type to "notification" (alternatively, try "_NET_WM_WINDOW_TYPE_DOCK")
        setWindowProperty(x11, display, xWindow, "_NET_WM_WINDOW_TYPE", "_NET_WM_WINDOW_TYPE_NOTIFICATION");
        // Set the window state to "above" so that it stays on top of normal windows.
        setWindowProperty(x11, display, xWindow, "_NET_WM_STATE", "_NET_WM_STATE_ABOVE");

        // Map the window and flush the changes.
        x11.XMapWindow(display, xWindow);
        x11.XFlush(display);
        x11.XCloseDisplay(display);
    }

    private static void setWindowProperty(X11 x11, X11.Display display, X11.Window window,
        String property, String value) {
		// Get atoms for the property and the value.
		X11.Atom propertyAtom = x11.XInternAtom(display, property, false);
		X11.Atom valueAtom = x11.XInternAtom(display, value, false);
		
		// Create a Memory block to hold the integer value.
		Memory mem = new Memory(Native.getNativeSize(Integer.TYPE));
		mem.setInt(0, valueAtom.intValue());
		
		// Call XChangeProperty. Note that XA_ATOM is accessed as a static field.
		x11.XChangeProperty(display, window, propertyAtom, X11.XA_ATOM, 32, X11.PropModeReplace, mem, 1);
	}
}
