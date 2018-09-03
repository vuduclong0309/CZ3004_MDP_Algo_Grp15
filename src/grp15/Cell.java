package grp15;

import static grp15.Maze.MAZE_HEIGHT;
import static grp15.Maze.MAZE_WIDTH;

enum CellState {
    BLOCKED, FREE, START, GOAL;
}

public class Cell {
    private int row, col;
    private CellState state;
    private boolean visited = false;

    public Cell(int row, int col, CellState state){
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public boolean isBorder() {
        if(this.row == 0 || this.row == MAZE_WIDTH-1 || this.col == MAZE_HEIGHT-1 || this.col == 0)
            return true;
        else
            return false;
    }

    public boolean isGoal() {
        if(this.col>=13 && this.row<=3)
            return true;
        else
            return false;
    }

    public boolean isStart() {
        if(this.col<=3 && this.row>=18)
            return true;
        else
            return false;
    }

    public boolean isVisited(){
        return this.visited;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setVisited(){
        this.visited = true;
    }
}
