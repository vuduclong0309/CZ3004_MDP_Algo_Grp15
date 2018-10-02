package grp15.object;


import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import static grp15.object.Robot.*;

public class Sensor {
    private final int lowerSensingRange;
    private final int upperSensingRange;
    private int sensorDir;

    private Robot r;
    private int displacementX; int displacementY;

    private final String id;

    public RobotOrientation getSensorOrientation(){
        int posX = r.getPosX() + displacementX;
        int posY = r.getPosY() + displacementY;
        int dir = NORTH;
        switch (this.sensorDir){
            case NORTH:
                dir = r.getDirection();
                break;
            case SOUTH:
                switch (r.getDirection()){
                    case NORTH:
                        dir = SOUTH;
                        break;
                    case SOUTH:
                        dir = NORTH;
                        break;
                    case EAST:
                        dir = WEST;
                        break;
                    case WEST:
                        dir = EAST;
                        break;
                }
                break;
            case EAST:
                switch (r.getDirection()){
                    case NORTH:
                        dir = EAST;
                        break;
                    case SOUTH:
                        dir = WEST;
                        break;
                    case EAST:
                        dir = SOUTH;
                        break;
                    case WEST:
                        dir = NORTH;
                        break;
                }
                break;
            case WEST:
                switch (r.getDirection()){
                    case NORTH:
                        dir = WEST;
                        break;
                    case SOUTH:
                        dir = EAST;
                        break;
                    case EAST:
                        dir = NORTH;
                        break;
                    case WEST:
                        dir = SOUTH;
                        break;
                }
                break;
        }

        return new RobotOrientation(new Pair(new Pair(posX, posY), dir));
    }


    public Sensor(String id, int lowerRangeValue, int upperRangeValue, int dx, int dy, int dir, Robot r) {
        this.r = r;
        this.id = id;
        this.lowerSensingRange = lowerRangeValue;
        this.upperSensingRange = upperRangeValue;
        this.displacementX = dx;
        this.displacementY = dy;
        this.sensorDir = dir;
    }

    /**
     * Uses the sensor direction and given value from the actual sensor to update the map.
     */
    public void physicalSense(MazeSolver exploredArenaMap, int sensorVal) {
        RobotOrientation orientation = getSensorOrientation();
        int posX = orientation.getPosX();
        int posY = orientation.getPosY();
        int dir =  orientation.getDirection();
        if(sensorVal == 0){
            for(int i = 1; i < sensorVal; i++) {
                if (lowerSensingRange == 1) {
                    switch (dir) {
                        case NORTH:
                            posX = posX + i;
                            break;
                        case SOUTH:
                            posX = posX - i;
                            break;
                        case EAST:
                            posY = posY + i;
                            break;
                        case WEST:
                            posY = posY - i;
                            break;
                    }
                    exploredArenaMap.getCell(posX, posY).setExplored();
                }
            }
        }
        else {
            for(int i = 1; i < sensorVal; i++){
                if(i < lowerSensingRange) continue;
                switch (dir){
                    case NORTH:
                        posX = posX + i;
                        break;
                    case SOUTH:
                        posX = posX - i;
                        break;
                    case EAST:
                        posY = posY + i;
                        break;
                    case WEST:
                        posY = posY - i;
                        break;
                }
                exploredArenaMap.getCell(posX, posY).setExplored();
                if(i==sensorVal) {
                    exploredArenaMap.getCell(posX, posY).setBlocked(i == sensorVal);
                    for(int dx = -1; dx <= 1; dx ++){
                        for(int dy = -1; dy <= 1; dy ++){
                            if(dx == 0 && dy == 0) continue;
                            exploredArenaMap.getCell(posX + dx, posX + dy).setVirtualWall(true);
                        }
                    }
                }
            }
        }
    }
}
