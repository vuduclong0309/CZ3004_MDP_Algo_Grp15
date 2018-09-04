package grp15.object;

public class Robot {
    public static final int NORTH = 1;
    public static final int SOUTH = 2;
    public static final int EAST = 3;
    public static final int WEST = 4;

    public static final int MOVE_FORWARD = 5;
    public static final int TURN_LEFT = 6;
    public static final int TURN_RIGHT = 7;
    public static final int STOP = 8;

    private int posX, posY;
    private int direction = NORTH;

    public Robot(){};

    public Robot(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public void moveForward() {
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
                break;
            case TURN_LEFT:
                this.turnLeft();
                break;
            case TURN_RIGHT:
                this.turnRight();
                break;
        }
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
}
