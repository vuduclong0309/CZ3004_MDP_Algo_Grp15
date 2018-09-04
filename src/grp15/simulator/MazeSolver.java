package grp15.simulator;

import grp15.object.Cell;
import grp15.object.CellColor;
import grp15.object.Robot;

import javax.swing.*;
import java.awt.*;

import static grp15.object.Cell.GRID_SIZE;
import static grp15.object.Robot.*;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;

public class MazeSolver extends JPanel implements CellColor {

    private Cell[][] mazeCell = new Cell[MAZE_HEIGHT][MAZE_WIDTH];
    private Robot robot;

    public MazeSolver(MazeEditor maze, Robot r) {
        this.robot = r;
        this.initiate(maze);
    }

    public void paintComponent(Graphics g)
    {
        //draw grid line
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(GRID_SIZE, GRID_SIZE, this.getWidth()-GRID_SIZE*2, this.getHeight()-GRID_SIZE*2);

        //draw maze wall
        for(int i = 0; i < MAZE_WIDTH; i++){
            g.setColor(Color.BLACK);
            g.fillRect((i+1)*GRID_SIZE - 2,0, 2, this.getHeight());
        }
        for(int i = 0; i < MAZE_HEIGHT; i++){
            g.setColor(Color.BLACK);
            g.fillRect(0,(i+1)*GRID_SIZE - 2, this.getWidth(), 2);
        }

        //draw arena
        for (int i= 0; i < MAZE_HEIGHT; i++) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                if(mazeCell[i][j].isExplored()) {
                    g.setColor(mazeCell[i][j].getColor());
                    g.fillRect(mazeCell[i][j].getCol()*GRID_SIZE, mazeCell[i][j].getRow()*GRID_SIZE, 30, 30);
                }
            }
        }


    }

    private void initiate(MazeEditor map) {
        for (int i = 0; i < MAZE_HEIGHT; i++) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                mazeCell[i][j] = new Cell(i, j);
                this.mazeCell[i][j].setColor(map.getMazeMap()[i][j].getColor());
                if (i==0 || i== MAZE_HEIGHT -1 || j==0 || j == MAZE_WIDTH - 1){
                    this.mazeCell[i][j].setExplored();
                }
            }
        }
    }

    public Cell explorePosition(int x, int y){
        mazeCell[x][y].setExplored();
        return mazeCell[x][y];
    }

    public Cell[][] getMazeCell() {
        return mazeCell;
    }

}
