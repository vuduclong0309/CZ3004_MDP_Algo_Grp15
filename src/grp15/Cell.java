package grp15;

enum CellState {
    UNKNOWN, BLOCKED, FREE, START, GOAL;
}

public class Cell {
    private int row, col;
    private CellState state;
}
