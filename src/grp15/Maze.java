package grp15;

public class Maze{
    public static int MAZE_WIDTH = 20;
    public static int MAZE_HEIGHT = 15;

    private Cell[][] mazeMap = new Cell[MAZE_WIDTH][MAZE_HEIGHT];
    private Robot robot;
    private int[][] maze = new int[MAZE_WIDTH][MAZE_HEIGHT];// for fastest path

    public Maze(Cell[][] cells){
        this.mazeMap = cells;
    }
}
