package grp15.object;

import grp15.rpi.Comms;
import grp15.simulator.MazeEditor;
import grp15.simulator.MazeSolver;
import grp15.object.Sensor;
import grp15.util.MapDescriptor;

import static grp15.Main.communicator;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;

public class Robot {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    public static final int MOVE_FORWARD = 5; //w
    public static final int TURN_LEFT = 6; //a
    public static final int TURN_RIGHT = 7; //d
    public static final int STOP = 8; //s
    public static final int START = 9; //c

    private int S_SENSOR_LOWER_RANGE_VALUE = 1;
    private int S_SENSOR_UPPER_RANGE_VALUE = 2;
    private int L_SENSOR_LOWER_RANGE_VALUE = 1;
    private int L_SENSOR_UPPER_RANGE_VALUE = 3;

    public static int sensorFShortest = 1;
    public static int sensorFLongest = 2;
    public static int sensorLShortest = 1;
    public static int sensorLLongest = 2;
    public static int sensorRShortest = 1;
    public static int sensorRLongest = 3;

    public static final int TURN_COST = 1;
    public static final int MOVE_COST = 1;

    private final Sensor SHORT_RANGE_FRONT_LEFT;
    private final Sensor SHORT_RANGE_FRONT_CENTER;
    private final Sensor SHORT_RANGE_FRONT_RIGHT;
    private final Sensor SHORT_RANGE_LEFT_FRONT;
    private final Sensor SHORT_RANGE_LEFT_BACK;
    private final Sensor LONG_RANGE_RIGHT;

    private int posX, posY;
    private int direction = NORTH;

    public Robot(int x, int y, int dir){
        this.posX = x;
        this.posY = y;
        this.direction = dir;
        SHORT_RANGE_FRONT_LEFT = new Sensor("srfl", S_SENSOR_LOWER_RANGE_VALUE, S_SENSOR_UPPER_RANGE_VALUE, 2, 0, NORTH, this);
        SHORT_RANGE_FRONT_CENTER = new Sensor("srfc", S_SENSOR_LOWER_RANGE_VALUE, S_SENSOR_UPPER_RANGE_VALUE, 2, 1, NORTH, this);
        SHORT_RANGE_FRONT_RIGHT = new Sensor("srfr", S_SENSOR_LOWER_RANGE_VALUE, S_SENSOR_UPPER_RANGE_VALUE, 2, 2, NORTH, this);
        LONG_RANGE_RIGHT = new Sensor("lrr", L_SENSOR_LOWER_RANGE_VALUE, L_SENSOR_UPPER_RANGE_VALUE, 2, 2, EAST, this);
        SHORT_RANGE_LEFT_FRONT = new Sensor("srlf", S_SENSOR_LOWER_RANGE_VALUE, S_SENSOR_UPPER_RANGE_VALUE, 2, 0, WEST, this);
        SHORT_RANGE_LEFT_BACK = new Sensor("srlb", S_SENSOR_LOWER_RANGE_VALUE, S_SENSOR_UPPER_RANGE_VALUE, 0, 0, WEST, this);
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
                communicator.sendMsg("w", "INSTR");
                moveForward();
                break;
            case TURN_LEFT:
                communicator.sendMsg("a", "INSTR");
                turnLeft();
                break;
            case TURN_RIGHT:
                communicator.sendMsg("d", "INSTR");
                turnRight();
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

    //method for a full sensor
    public void getSensorData(MazeSolver map) {
        int[] result = new int[6];

        String msg = communicator.recvMsg();
        String[] msgArr = msg.split(" ");

        if (msgArr[0].equals(Comms.SENSOR_DATA)) {
           /* result[0] = Integer.parseInt(msgArr[1].split(":")[1]);
            result[1] = Integer.parseInt(msgArr[2].split(":")[1]);
            result[2] = Integer.parseInt(msgArr[3].split(":")[1]);
            result[3] = Integer.parseInt(msgArr[4].split(":")[1]);
            result[4] = Integer.parseInt(msgArr[5].split(":")[1]);
            result[5] = Integer.parseInt(msgArr[6].split(":")[1]);
            */
            result[0] = Integer.parseInt(msgArr[1]);
            result[1] = Integer.parseInt(msgArr[2]);
            result[2] = Integer.parseInt(msgArr[3]);
            result[3] = Integer.parseInt(msgArr[4]);
            result[4] = Integer.parseInt(msgArr[5]);
            result[5] = Integer.parseInt(msgArr[6]);

            for(int i = 1; i <= 6; i++){
                System.out.print(msgArr[i]+" ");
            }
            System.out.println("");
        }

        System.out.println("SRFL");
        SHORT_RANGE_FRONT_LEFT.physicalSense(map, result[0]);
        System.out.println("SRFC");
        SHORT_RANGE_FRONT_CENTER.physicalSense(map, result[1]);
        System.out.println("SRFR");
        SHORT_RANGE_FRONT_RIGHT.physicalSense(map, result[2]);
        System.out.println("LRR");
        LONG_RANGE_RIGHT.physicalSense(map, result[3]);
        System.out.println("SRLF");
        SHORT_RANGE_LEFT_FRONT.physicalSense(map, result[4]);
        System.out.println("SRLB");
        SHORT_RANGE_LEFT_BACK.physicalSense(map, result[5]);
        map.repaint();

        String mapUpdate = MapDescriptor.toAndroid(map);

        communicator.sendMsg(mapUpdate + " " + toDirectionString(this.getDirection()) + " " + this.getPosX() + " " + this.getPosY(), Comms.MAP_STRINGS);
    }

    public static String toDirectionString(int dir){
        String res = "NSEW";

        switch (dir){
            case 0:
                res = "NORTH";
                break;
            case 1:
                res = "SOUTH";
                break;
            case 2:
                res = "EAST";
                break;
            case 3:
                res = "WEST";
                break;
        }

        return res;
    }

    public static boolean isValidPosition(int x, int y){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++) if(MazeEditor.isValidPosition(x + i, y + j) == false) return false;
        }
        return true;
    }

    public void setPos(int x, int y, int dir){
        this.posX = x;
        this.posY = y;
        this.direction = dir;
    }
}