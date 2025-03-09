package com.keyfull;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

public class MacOverlayWindow {

    // Minimal binding for the Objective-C runtime (Foundation)
    public interface Foundation extends Library {
        Foundation INSTANCE = Native.load("Foundation", Foundation.class);
        Pointer objc_getClass(String className);
        Pointer sel_registerName(String selectorName);
        Pointer objc_msgSend(Pointer receiver, Pointer selector, Object... args);
    }

    public static void main(String[] args) {
        // IMPORTANT: Run with -XstartOnFirstThread so that UI calls occur on the main thread.
        createTopmostWindow();
    }

    public static void createTopmostWindow() {
        final Foundation foundation = Foundation.INSTANCE;

        // Create an autorelease pool
        Pointer poolClass = foundation.objc_getClass("NSAutoreleasePool");
        Pointer allocSel = foundation.sel_registerName("alloc");
        Pointer initSel = foundation.sel_registerName("init");
        Pointer pool = foundation.objc_msgSend(poolClass, allocSel);
        pool = foundation.objc_msgSend(pool, initSel);

        // Get the shared NSApplication instance
        Pointer nsApplicationClass = foundation.objc_getClass("NSApplication");
        Pointer sharedAppSel = foundation.sel_registerName("sharedApplication");
        Pointer app = foundation.objc_msgSend(nsApplicationClass, sharedAppSel);

        // Create an NSWindow instance:
        Pointer nsWindowClass = foundation.objc_getClass("NSWindow");
        Pointer windowAllocSel = foundation.sel_registerName("alloc");
        Pointer windowInitSel = foundation.sel_registerName("initWithContentRect:styleMask:backing:defer:");

        // Define window frame (CGRect: x, y, width, height)
        Memory rect = new Memory(4 * Native.getNativeSize(Double.TYPE));
        rect.setDouble(0, 200);                                      // x
        rect.setDouble(Native.getNativeSize(Double.TYPE), 200);      // y
        rect.setDouble(2 * Native.getNativeSize(Double.TYPE), 400);    // width
        rect.setDouble(3 * Native.getNativeSize(Double.TYPE), 200);    // height

        // Parameters: styleMask = 0 (borderless), backing = 2 (buffered), defer = NO (false)
        long styleMask = 0x00000002; // Borderless window style
        long backing = 2;
        boolean defer = false;

        // Allocate and initialize the window
        Pointer window = foundation.objc_msgSend(nsWindowClass, windowAllocSel);
        window = foundation.objc_msgSend(window, windowInitSel, rect, styleMask, backing, defer);

        // Set window level (e.g., NSDockWindowLevel which is typically 24)
        Pointer setLevelSel = foundation.sel_registerName("setLevel:");
        int nsDockLevel = 24;
        foundation.objc_msgSend(window, setLevelSel, nsDockLevel);

        // Configure transparency: set the window to be non-opaque and set alpha value to 0.8 (80% opacity)
        Pointer setOpaqueSel = foundation.sel_registerName("setOpaque:");
        foundation.objc_msgSend(window, setOpaqueSel, false);
        Pointer setAlphaSel = foundation.sel_registerName("setAlphaValue:");
        foundation.objc_msgSend(window, setAlphaSel, 0.8f);

        // Make the window key and visible
        Pointer makeKeyAndOrderFrontSel = foundation.sel_registerName("makeKeyAndOrderFront:");
        foundation.objc_msgSend(window, makeKeyAndOrderFrontSel, Pointer.NULL);

        // Drain the autorelease pool (cleanup)
        Pointer drainSel = foundation.sel_registerName("drain");
        foundation.objc_msgSend(pool, drainSel);

        // Start the NSApplication main loop
        Pointer runSel = foundation.sel_registerName("run");
        foundation.objc_msgSend(app, runSel);
    }
}
