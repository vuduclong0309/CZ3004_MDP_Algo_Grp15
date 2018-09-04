package grp15.object;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static grp15.object.MazeEditor.MAZE_HEIGHT;
import static grp15.object.MazeEditor.MAZE_WIDTH;


public class Cell extends JPanel implements CellColor{
    private int row, col;
    private boolean explored = false;
    private Color color;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;

        initializeCell();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFrame frame = null;
                if(color == BLOCKED){
                    setBackground(FREE);
                    setColor(FREE);
                }
                else if(color != START && color != GOAL){
                    setBackground(BLOCKED);
                    setColor(BLOCKED);
                }
                else
                    JOptionPane.showMessageDialog(frame,"Cannot put walls in start or goal area ! ");

            }
        });
    }

    public boolean isBlocked() {
        return (this.row == 0 || this.row == MAZE_WIDTH - 1 || this.col == MAZE_HEIGHT - 1 || this.col == 0);
    }

    public boolean isStart() {
        return (this.row <= 3 && this.col<=3);
    }

    public boolean isGoal() {
        return (this.col>=MAZE_HEIGHT - 4 && this.row >= MAZE_WIDTH - 4);
    }

    public boolean isExplored(){
        return this.explored;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setColor(Color c){
        this.color = c;
    }
    public void setExplored(){
        this.explored = true;
    }

    public void setUnxplored(){
        this.explored = false;
    }

    private void initializeCell() {
        if(this.isBlocked()) {
            this.setBackground(BLOCKED);
            this.setColor(BLOCKED);
        } else if( this.isStart() ){
            this.setBackground(START);
            this.setColor(START);
            this.setExplored();
        } else if( this.isGoal() ){
            this.setBackground(GOAL);
            this.setColor(GOAL);
            this.setExplored();
        } else {
            this.setBackground(FREE);
            this.setColor(FREE);
        }
    }
}
