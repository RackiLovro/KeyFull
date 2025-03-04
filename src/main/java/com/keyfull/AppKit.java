package com.keyfull;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface AppKit extends Library {
    AppKit INSTANCE = Native.load("AppKit", AppKit.class);

    // Window level constants (from macOS documentation)
    int NSNormalWindowLevel = 0;
    int NSFloatingWindowLevel = 3;
    int NSMainMenuWindowLevel = 24;
    int NSStatusWindowLevel = 25;  // typically above the menu bar

    // Objective-C runtime methods
    Pointer sel_registerName(String selectorName);
    Pointer objc_msgSend(Pointer receiver, Pointer selector, int value);
}
