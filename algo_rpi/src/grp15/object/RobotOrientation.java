package grp15.object;

import javafx.util.Pair;

import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import static grp15.object.Robot.*;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;


public class RobotOrientation {
    private int posX, posY, direction;

    public RobotOrientation(Robot r){
        this.posX = r.getPosX();
        this.posY = r.getPosY();
        this.direction = r.getDirection();
    }

    public RobotOrientation(RobotOrientation r){
        this.posX = r.getPosX();
        this.posY = r.getPosY();
        this.direction = r.getDirection();
    }


    public RobotOrientation(int x, int y, int dir){
        this.posX = x;
        this.posY = y;
        this.direction = dir;
    }

    public RobotOrientation(Pair<Pair<Integer, Integer>, Integer> pi){
        this.posX = pi.getKey().getKey();
        this.posY = pi.getKey().getValue();
        this.direction = pi.getValue();
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }

    public int getDirection(){
        return this.direction;
    }

    public RobotOrientation moveForward() {
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
        return this;
    }

    public RobotOrientation turnLeft() {
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
        return this;
    }

    public RobotOrientation turnRight() {
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
        return this;
    }

    public Pair<Pair<Integer, Integer>, Integer> toPairFormat(){
        return new Pair(new Pair(posX,posY),direction);
    }

    public boolean isEqual(RobotOrientation r){
        return (this.posX == r.getPosX()) && (this.posY == r.getPosY()) && (this.getDirection() == r.getDirection());
    }

    int falseSenseFrontSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 3 front short sensors
        boolean wall[] = new boolean[3];
        wall[0] = false; wall[1] = false; wall[2] = false;
        int i; int res = 0;
        for (i = sensorFShortest; i <= sensorFLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case NORTH:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isExplored() == true) {
                                if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posX - i > 0) {
                            if(map.getMazeCell()[this.posX - i][this.posY + j].isExplored() == true) {
                                if(map.getMazeCell()[this.posX - i][this.posY + j].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case EAST:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isExplored() == true) {
                                if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case WEST:
                        if (this.posY - i > 0) {
                            if(map.getMazeCell()[this.posX + j][this.posY - i].isExplored() == true) {
                                if(map.getMazeCell()[this.posX + j][this.posY - i].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
        return res;
    }

    int falseSenseRightSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 1 long right sensor at first row of robot
        boolean wall[] = new boolean[3];
        wall[0] = true; wall[1] = true; wall[2] = false;
        int i; int res = 0;
        for (i = 1; i <= sensorRLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case WEST:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            if(i >= sensorRShortest){
                                if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isExplored()){
                                    if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked()) wall[j] = true;
                                    continue;
                                }
                                else{
                                    res++;
                                    wall[j]=true;
                                }
                            }
                        } else
                            wall[j] = true;
                        break;
                    case EAST:
                        if (this.posX - i >= 0) {
                            if(i >= sensorRShortest) {
                                if (map.getMazeCell()[this.posX - i][this.posY + j].isExplored()) {
                                    if(map.getMazeCell()[this.posX - i][this.posY + j].isBlocked()) wall[j] = true;
                                    continue;
                                }
                                else {
                                    res++;
                                    wall[j] = true;
                                }
                            }
                        } else
                            wall[j] = true;
                        break;
                    case NORTH:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            if(i >= sensorRShortest) {
                                if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isExplored()){
                                    if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked()) wall[j] = true;
                                    continue;
                                }
                                else{
                                    res++;
                                    wall[j]=true;
                                }
                            }
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posY - i > 0) {
                            if(i >= sensorRShortest) {
                                if(map.getMazeCell()[this.posX + j][this.posY - i].isExplored()){
                                    if(map.getMazeCell()[this.posX + j][this.posY - i].isBlocked()) wall[j] = true;
                                    continue;
                                }
                                else{
                                    res++;
                                    wall[j]=true;
                                }
                            }
                            wall[j] = map.getMazeCell()[this.posX + j][this.posY - i].isBlocked();
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
        return res;
    }

    int falseSenseLeftSensorData(MazeSolver map) {
        //this part goes according to position and number of sensors
        //we have 1 left short sensor at the back left
        boolean wall[] = new boolean[3];
        wall[0] = false; wall[1] = true; wall[2] = false;
        int i; int res = 0;
        for (i = sensorLShortest; i <= sensorLLongest; i++) {
            for(int j = 0; j < 3; j++) {
                if(wall[j] == true) continue;
                switch (this.direction) {
                    case EAST:
                        if (this.posX + 2 + i <= MAZE_HEIGHT - 1) {
                            if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isExplored()) {
                                if(map.getMazeCell()[this.posX + 2 + i][this.posY + j].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case WEST:
                        if (this.posX - i > 0) {
                            if(map.getMazeCell()[this.posX - i][this.posY + j].isExplored() == true){
                                if(map.getMazeCell()[this.posX - i][this.posY + j].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case SOUTH:
                        if (this.posY + 2 + i <= MAZE_WIDTH - 1) {
                            if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isExplored() == true){
                                if(map.getMazeCell()[this.posX + j][this.posY + 2 + i].isBlocked()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                    case NORTH:
                        if (this.posY - i > 0) {
                            if(map.getMazeCell()[this.posX + j][this.posY - i].isExplored() == true){
                                if(map.getMazeCell()[this.posX + j][this.posY - i].isExplored()) wall[j] = true;
                                continue;
                            }
                            else{
                                res++;
                                wall[j]=true;
                            }
                        } else
                            wall[j] = true;
                        break;
                }
            }
        }
        return res;
    }

    //method for a full sensor
    public int falseSenseSensorData(MazeSolver map) {
        return this.falseSenseFrontSensorData(map)
                + this.falseSenseLeftSensorData(map)
                + this.falseSenseRightSensorData(map);
    }
}