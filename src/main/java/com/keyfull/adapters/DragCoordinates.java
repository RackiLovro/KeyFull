package src.main.java.com.keyfull.adapters;

public class DragCoordinates {
    private int[] start = null;
    private int[] end = null;

    public boolean isStartEmpty() {
        return start == null;
    }

    public void setStart(int row, int col, char key) {
        start = new int[]{row, col, key};
    }

    public void setEnd(int row, int col, char key) {
        end = new int[]{row, col, key};
    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }
}
