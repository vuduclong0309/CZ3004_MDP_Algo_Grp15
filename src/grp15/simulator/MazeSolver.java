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
    private MazeEditor maze;
    private Robot robot;

    public MazeSolver(MazeEditor maze, Robot r) {
        this.robot = r;
        this.maze = maze;
    }

    public void paintComponent(Graphics g)
    {
        //draw arena background (wall)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(GRID_SIZE, GRID_SIZE, this.getWidth()-GRID_SIZE*2, this.getHeight()-GRID_SIZE*2);

        //draw grid line
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
                if(maze.getMazeMap()[i][j].isExplored()) {
                    g.setColor(maze.getMazeMap()[i][j].getColor());
                } else{
                    g.setColor(Color.GRAY);
                }
                g.fillRect(maze.getMazeMap()[i][j].getCol()*GRID_SIZE,(MAZE_HEIGHT - 1 - maze.getMazeMap()[i][j].getRow())*GRID_SIZE,30, 30);
            }
        }


        //robot

        g.setColor(ROBOT_BODY);
        g.fillRect(this.robot.getPosY()*GRID_SIZE, (MAZE_HEIGHT - 1 - 2 - this.robot.getPosX())*GRID_SIZE, 96, 96);

        switch (this.robot.getDirection()){
            case NORTH: 	g.setColor(ROBOT_HEAD);
                g.fillRect((this.robot.getPosY()+1)*GRID_SIZE, (MAZE_HEIGHT - 1 - (this.robot.getPosX()+2))*GRID_SIZE, 30, 30);
                break;
            case SOUTH:  g.setColor(ROBOT_HEAD);
                g.fillRect((this.robot.getPosY()+1)*GRID_SIZE, (MAZE_HEIGHT - 1 - this.robot.getPosX())*GRID_SIZE, 30, 30);
                break;
            case EAST: g.setColor(ROBOT_HEAD);
                g.fillRect((this.robot.getPosY()+2)*GRID_SIZE, (MAZE_HEIGHT - 1 - (this.robot.getPosX()+1))*GRID_SIZE, 30, 30);
                break;
            case WEST: g.setColor(ROBOT_HEAD);
                g.fillRect(this.robot.getPosY()*GRID_SIZE, (MAZE_HEIGHT - 1 - (this.robot.getPosX()+1))*GRID_SIZE, 30, 30);
                break;
        }
    }

    public void senseMap(){
        this.robot.getSensorData(this);
        /*int posX = robot.getPosX(), posY = robot.getPosY();
        maze.getMazeMap()[posX-1][posY].setExplored();
        maze.getMazeMap()[posX-1][posY+1].setExplored();
        maze.getMazeMap()[posX-1][posY+2].setExplored();
        maze.getMazeMap()[posX+3][posY].setExplored();
        maze.getMazeMap()[posX+3][posY+1].setExplored();
        maze.getMazeMap()[posX+3][posY+2].setExplored();
        maze.getMazeMap()[posX][posY-1].setExplored();
        maze.getMazeMap()[posX+1][posY-1].setExplored();
        maze.getMazeMap()[posX+2][posY-1].setExplored();
        maze.getMazeMap()[posX][posY+3].setExplored();
        maze.getMazeMap()[posX+1][posY+3].setExplored();
        maze.getMazeMap()[posX+2][posY+3].setExplored();*/
    }

    public Cell explorePosition(int x, int y){
        maze.getMazeMap()[x][y].setExplored();
        return maze.getMazeMap()[x][y];
    }

    public Cell[][] getMazeCell() {
        return maze.getMazeMap();
    }

    public Robot getRobot() {
        return robot;
    }

    public boolean isValidPosition(int row, int col) {
        return maze.isValidPosition(row, col);
    }
}