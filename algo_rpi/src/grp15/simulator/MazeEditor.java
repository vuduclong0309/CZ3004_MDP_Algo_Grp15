/**
 *      created by vuduclong0309
 */
package grp15.simulator;

import grp15.algorithm.Explorer;
import grp15.object.Cell;
import grp15.object.CellColor;

import javax.swing.*;
import java.awt.*;

public class MazeEditor extends JPanel implements CellColor {
    public static int MAZE_HEIGHT = 22;
    public static int MAZE_WIDTH = 17;

    private JPanel[][] mapGUI = new JPanel[MAZE_HEIGHT][MAZE_WIDTH];
    private Cell[][] mazeMap = new Cell[MAZE_HEIGHT][MAZE_WIDTH];

    public MazeEditor(){
        initiate();
        //mazeMap[Explorer.WAYPOINT_X][Explorer.WAYPOINT_Y].setColor(WAYPOINT);
        for(int i = 1; i < MAZE_HEIGHT - 1; i++) {
            mazeMap[i][1].setVirtualWall(true);
            mazeMap[i][MAZE_WIDTH - 2].setVirtualWall(true);
        }

        for(int i = 1; i < MAZE_WIDTH - 1; i++) {
            mazeMap[1][i].setVirtualWall(true);
            mazeMap[MAZE_HEIGHT - 2][i].setVirtualWall(true);
        }
    };
    protected void initiate() {
        this.setLayout(new GridLayout(MAZE_HEIGHT, MAZE_WIDTH));
        for (int i = MAZE_HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                mazeMap[i][j] = new Cell(i, j);
                if(i==0 || j == 0 || i == MAZE_HEIGHT - 1 || j == MAZE_WIDTH - 1) mazeMap[i][j].setExplored();
                mazeMap[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                mapGUI[i][j] = mazeMap[i][j];
                this.add(mazeMap[i][j]);
            }
        }
    }
    public void updateLoadedMap(char[][] mapEncoding) {
        for (int i = 0; i < MAZE_HEIGHT - 2; i++) {
            for (int j = 0; j < MAZE_WIDTH - 2; j++) {
                if (mapEncoding[i][j] == '1') {
                    mazeMap[i+1][j+1].setColor(BLOCKED);
                }
            }
        }
    }


    public Cell[][] getMazeMap(){
        return this.mazeMap;
    }

    public static boolean isValidPosition(int row, int col) {
        return row >= 0 && col >= 0 && row < MAZE_HEIGHT && col < MAZE_WIDTH;
    }
}
