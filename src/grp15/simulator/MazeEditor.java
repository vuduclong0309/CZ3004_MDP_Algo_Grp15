package grp15.simulator;

import grp15.object.Cell;
import grp15.object.CellColor;

import javax.swing.*;
import java.awt.*;

public class MazeEditor extends JPanel implements CellColor {
    public static int MAZE_HEIGHT = 22;
    public static int MAZE_WIDTH = 17;

    private JPanel[][] mapGUI = new JPanel[MAZE_HEIGHT][MAZE_WIDTH];
    private Cell[][] mazeMap = new Cell[MAZE_HEIGHT][MAZE_WIDTH];

    public MazeEditor(){ initiate(); };
    protected void initiate() {
        this.setLayout(new GridLayout(MAZE_HEIGHT, MAZE_WIDTH));
        for (int i = 0; i < MAZE_HEIGHT; i++) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                mazeMap[i][j] = new Cell(i, j);
                mazeMap[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                //mapGUI[i][j] = mazeMap[i][j];
                this.add(mazeMap[i][j]);
            }
        }

    }
    public void updateLoadedMap(char[][] mapEncoding) {
        for (int i = 0; i < MAZE_HEIGHT ; i++) {
            for (int j = 0; j < MAZE_WIDTH; j++) {
                if (mapEncoding[i][j] == '1') {
                    mazeMap[i][j].setColor(BLOCKED);
                }


            }


        }
    }


    public Cell[][] getMazeMap(){
        return this.mazeMap;
    }
}
