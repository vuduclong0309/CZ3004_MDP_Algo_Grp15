package grp15;

public class Maze{
    static int DEFAULT_WIDTH = 20;
    static int DEFAULT_HEIGHT = 20;

    private int width, height;
    private Cell[][] mazeMap;

    public Maze(){
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    public Maze(Cell[][] cells, int width, int height){
        this.mazeMap = cells;
        this.width = width;
        this.height = height;
    }
}
