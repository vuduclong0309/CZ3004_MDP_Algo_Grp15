package grp15;

import static grp15.Direction.*;

enum Direction {
    NORTH, SOUTH, EAST, WEST;
}

public class Robot {
    private int pos_x, pos_y;
    private Direction direction = NORTH;

    public Robot(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }

    public void moveForward() {
        switch (this.direction) {
            case NORTH:
                this.pos_y--;
                break;
            case SOUTH:
                this.pos_y++;
                break;
            case EAST:
                this.pos_x++;
                break;
            case WEST:
                this.pos_x--;
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

    public Direction getDirection() {
        return this.direction;
    }
}
