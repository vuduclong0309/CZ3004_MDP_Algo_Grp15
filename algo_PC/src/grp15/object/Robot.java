package grp15.object;

import grp15.simulator.MazeEditor;
import grp15.simulator.MazeSolver;
import java.util.ArrayList;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;

public class Robot {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    public static final int MOVE_FORWARD = 5;
    public static final int TURN_LEFT = 6;
    public static final int TURN_RIGHT = 7;
    public static final int STOP = 8;

    public static int sensorFShortest = 1;
    public static int sensorFLongest = 1;
    public static int sensorLShortest = 1;
    public static int sensorLLongest = 1;
    public static int sensorRShortest = 1;
    public static int sensorRLongest = 3;

    public static final int TURN_COST = 1;
    public static final int MOVE_COST = 1;
    int totalMove = 0;
    int totalTurn = 0;
    private int posX, posY;
    private int direction = NORTH;

    public Robot(){};

    public Robot(int x, int y, int dir){
        this.posX = x;
        this.posY = y;
        this.direction = dir;
    }

    public void moveForward() {
        switch (this.direction) {
            case NORTH:
                this.posX++;
                break;
            case SOUTH:
                this.posX--;
                break;
            case EAST:
                this.posY++;
                break;
            case WEST:
                this.posY--;
                break;
        }
    }

    void turnLeft() {
        switch (this.direction) {
            case NORTH:
                this.direction = WEST;
                break;
            case EAST:
                this.direction = NORTH;
                break;
            case WEST:
                this.direction = SOUTH;
                break;
            case SOUTH:
                this.direction = EAST;
                break;
        }
    }

    public void turnRight() {
        switch (this.direction) {
            case NORTH:
                this.direction = EAST;
                break;
            case EAST:
                this.direction = SOUTH;
                break;
            case WEST:
                this.direction = NORTH;
                break;
            case SOUTH:
                this.direction = WEST;
                break;
        }
    }

    public void turnBack() {
        this.turnLeft();
        this.turnLeft();
    }

    public void moveRobot(int signal){
        switch (signal){
            case MOVE_FORWARD:
                this.moveForward();
                totalMove++;
                break;
            case TURN_LEFT:
                this.turnLeft();
                totalTurn++;
                break;
            case TURN_RIGHT:
                this.turnRight();
                totalTurn++;
                break;
        }
    }

    public RobotOrientation getOrientation(){
        return new RobotOrientation(this);
    }

    public int getDirection() {
        return this.direction;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    void getFrontSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 3 front short sensors
        boolean wall[] = new boolean[3];
        wall[0] = false; wall[1] = false; wall[2] = false;
        int i;
        for (i = sensorFShortest; i <= this.sensorFLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case NORTH:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            map.getMazeCell()[this.posX + 2 + i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posX - i > 0) {
                            map.getMazeCell()[this.posX - i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX - i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case EAST:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            map.getMazeCell()[this.posX + j][this.posY + 2 + i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case WEST:
                        if (this.posY - i > 0) {
                            map.getMazeCell()[this.posX + j][this.posY - i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY - i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
    }

    void getRightSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 1 long right sensor at first row of robot
        boolean wall[] = new boolean[3];
        wall[0] = true; wall[1] = true; wall[2] = false;
        int i;
        for (i = 1; i <= this.sensorRLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case WEST:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            if(i >= sensorRShortest) map.getMazeCell()[this.posX + 2 + i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case EAST:
                        if (this.posX - i >= 0) {
                            if(i >= sensorRShortest) map.getMazeCell()[this.posX - i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX - i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case NORTH:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            if(i >= sensorRShortest) map.getMazeCell()[this.posX + j][this.posY + 2 + i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posY - i > 0) {
                            if(i >= sensorRShortest) map.getMazeCell()[this.posX + j][this.posY - i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY - i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
    }

    void getLeftSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 1 left short sensor at the back left
        boolean wall[] = new boolean[3];
        wall[0] = false; wall[1] = true; wall[2] = false;
        int i;
        for (i = sensorLShortest; i <= this.sensorLLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case EAST:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            map.getMazeCell()[this.posX + 2 + i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case WEST:
                        if (this.posX - i > 0) {
                            map.getMazeCell()[this.posX - i][this.posY + j].setExplored();
                            wall[j] = map.getMazeCell()[this.posX - i][this.posY + j].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            map.getMazeCell()[this.posX + j][this.posY + 2 + i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                    case NORTH:
                        if (this.posY - i > 0) {
                            map.getMazeCell()[this.posX + j][this.posY - i].setExplored();
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY - i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
    }

    //method for a full sensor
    public void getSensorData(MazeSolver map) {
        this.getFrontSensorData(map);
        this.getLeftSensorData(map);
        this.getRightSensorData(map);
    }

    public static boolean isValidPosition(int x, int y){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++) if(MazeEditor.isValidPosition(x + i, y + j) == false) return false;
        }
        return true;
    }

    public int getTotalMove(){
        return this.totalMove;
    }

    public int getTotalTurn(){
        return this.totalTurn;
    }
}