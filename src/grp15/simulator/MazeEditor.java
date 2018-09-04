package grp15.simulator;

import grp15.object.Cell;

import javax.swing.*;
import java.awt.*;

public class MazeEditor extends JPanel {
    public static int MAZE_WIDTH = 20;
    public static int MAZE_HEIGHT = 15;

    private JPanel[][] mapGUI = new JPanel[MAZE_WIDTH][MAZE_HEIGHT];
    private Cell[][] mazeMap = new Cell[MAZE_WIDTH][MAZE_HEIGHT];

    public MazeEditor(){ initiate(); };
    protected void initiate() {
        this.setLayout(new GridLayout(MAZE_WIDTH, MAZE_HEIGHT));
        for (int i = 0; i < MAZE_WIDTH; i++) {
            for (int j = 0; j < MAZE_HEIGHT; j++) {
                mazeMap[i][j] = new Cell(i, j);
                mazeMap[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                mapGUI[i][j] = mazeMap[i][j];
                this.add(mazeMap[i][j]);
            }
        }
    }
}
