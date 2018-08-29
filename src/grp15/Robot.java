package grp15;

enum Direction {
    NORTH, SOUTH, EAST, WEST;
}

public class Robot {
    private int pos_x, pos_y;
    private Direction direction = Direction.NORTH;

    public Robot(int x, int y){
        this.pos_x = x;
        this.pos_y = y;
    }
}
