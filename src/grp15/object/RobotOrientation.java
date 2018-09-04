package grp15.object;

import grp15.object.Robot.*;

import static grp15.object.Robot.*;

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
                this.posY--;
                break;
            case SOUTH:
                this.posY++;
                break;
            case EAST:
                this.posX++;
                break;
            case WEST:
                this.posX--;
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

}
