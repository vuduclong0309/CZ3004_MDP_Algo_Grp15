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
        int posX;
        int posY;
        int dir = NORTH;
        switch (this.sensorDir){
            case NORTH:
                posX = r.getPosX() + displacementX;
                posY = r.getPosY() + displacementY;
                dir = r.getDirection();
                break;
            case SOUTH:
                posX = r.getPosX() + 2 - displacementX;
                posY = r.getPosY() + displacementY;
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
                posX = r.getPosX() + 2 - displacementY;
                posY = r.getPosY() + displacementX;
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
                posX = r.getPosX() + displacementY;
                posY = r.getPosY() + 2 - displacementX;
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
        int posX = orientation.getPosX() - 1;
        int posY = orientation.getPosY() - 1;
        int dir =  orientation.getDirection();
        int nPosX = 0; int nPosY = 0;
        System.out.println("Robot" + r.getPosX() + " " + r.getPosY() + " " + r.getDirection());
        System.out.println("Sensor" + posX + " " + posY + " " + dir + " " + sensorVal);
        if(sensorVal == 0){
            for(int i = 1; i <= upperSensingRange; i++) {
                if (lowerSensingRange == 1) {
                    switch (dir) {
                        case NORTH:
                            nPosX = posX + i;
                            nPosY = posY;
                            break;
                        case SOUTH:
                            nPosX = posX - i;
                            nPosY = posY;
                            break;
                        case EAST:
                            nPosX = posX;
                            nPosY = posY + i;
                            break;
                        case WEST:
                            nPosX = posX;
                            nPosY = posY - i;
                            break;
                    }
                    System.out.println("explore" + nPosX + " " + nPosY);
                    exploredArenaMap.getCell(nPosX, nPosY).setExplored();
                }
            }
        }
        else {
            for(int i = 1; i <= sensorVal; i++){
                if(i < lowerSensingRange) continue;
                switch (dir){
                    case NORTH:
                        nPosX = posX + i;
                        nPosY = posY;
                        break;
                    case SOUTH:
                        nPosX = posX - i;
                        nPosY = posY;
                        break;
                    case EAST:
                        nPosX = posX;
                        nPosY = posY + i;
                        break;
                    case WEST:
                        nPosX = posX;
                        nPosY = posY - i;
                        break;
                }
                exploredArenaMap.getCell(nPosX, nPosY).setExplored();
                if(i==sensorVal) {
                    System.out.println("Set wall " + nPosX + " " + nPosY);
                    exploredArenaMap.getCell(nPosX, nPosY).setBlocked(true);
                    for(int dx = -1; dx <= 1; dx ++){
                        for(int dy = -1; dy <= 1; dy ++){
                            if(dx == 0 && dy == 0) continue;
                            exploredArenaMap.getCell(nPosX + dx, nPosX + dy).setVirtualWall(true);
                        }
                    }
                }
            }
        }
    }
}
