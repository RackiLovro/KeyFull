# KeyFull

This Java application allows you to control your computer without using a mouse. It works by displaying a grid on your screen with a simple double-letter coordinate system. You can move your mouse cursor by entering the coordinates corresponding to the location you want to move it to.

## Features

- **Grid-based mouse control:** Use a coordinate system (e.g., AA, AB, AC, ...) to move the mouse to specific positions on the screen.
- **No need for a mouse:** Completely control the mouse pointer without a physical mouse or trackpad.
- **Multiple monitor support:** Move your mouse across multiple screens
- **Java 21 required:** The program requires Java 21 to run.

## How It Works

1. **Start the Program**  
   Run the application using the following command:
   ```bash
   java -jar keyfull.jar
   ```

2. **Enter the Coordinates**  
   Once the program is running, look at the position on the screen where you want your mouse cursor to go. Then, select the screen, enter the desired column and row coordinates in the format of a double letter (e.g., AA, AB, AC, etc.).

3. **Final Move**  
   After entering the column and row coordinates, press the corresponding third and final letter to move the cursor to the desired location.
   
<img width="1509" alt="image" src="https://github.com/user-attachments/assets/7fd2a95b-8d27-40e2-b5bf-a842bcfa814e" />
---

## Setting Up a Custom Shortcut (Ubuntu)

To make using this program easier, you can create a custom keyboard shortcut to run the program. Here’s how to set it up in Ubuntu:

### Step 1: Open the Keyboard Shortcuts Settings
- Press `Super (Windows key)` and search for "Settings".
- Navigate to **Keyboard > Shortcuts**.

### Step 2: Add a New Custom Shortcut
- Scroll down and click on **+** to add a new shortcut.
- In the **Name** field, you can write something like "Mouse Mover".
- In the **Command** field, enter the following command to launch your program:
  ```bash
  java -jar /path/to/keyfull.jar
  ```
  Make sure to replace `/path/to/keyfull.jar` with the actual path to your `keyfull.jar` file.

### Step 3: Set the Shortcut Key
- After adding the command, click on **Set Shortcut** and press `Ctrl + Space` (or another combination of your choice).

Now, every time you press `Ctrl + Space`, your Mouse Mover program will launch, allowing you to control your mouse without the need for a physical device.

---

## Prerequisites

- **Java 21**: Make sure you have Java 21 installed on your system. You can check your Java version by running:
  ```bash
  java -version
  ```
  If you don't have it installed, you can install it using the following commands:
  ```bash
  sudo apt update
  sudo apt install openjdk-21-jdk
  ```

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
