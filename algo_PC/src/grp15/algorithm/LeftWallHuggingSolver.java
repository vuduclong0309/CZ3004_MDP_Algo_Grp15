package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import javafx.util.Pair;

import java.util.*;

import static grp15.object.Robot.*;
import static grp15.object.Robot.TURN_RIGHT;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;

public class LeftWallHuggingSolver {
    Cell[][] mazeMap;
    Robot robot;

    int turnCost, moveCost;
    public LeftWallHuggingSolver(Cell[][] maze, Robot r){
        this.mazeMap = maze;
        this.robot = r;
    };

    public ArrayList<Integer> getMove(){
        ArrayList<Integer> move;
        RobotOrientation nextpos = new RobotOrientation(this.robot);
        System.out.println(nextpos.toPairFormat());
        nextpos.turnLeft();
        System.out.println(nextpos.toPairFormat());
        nextpos.moveForward();
        System.out.println(nextpos.toPairFormat());
        System.out.println("left");
        if(isValidPosition(nextpos)) {
            move = new ArrayList<Integer>(2);
            move.add(TURN_LEFT);
            move.add(MOVE_FORWARD);
            return move;
        }
        System.out.println("forward");
        nextpos = new RobotOrientation(this.robot);
        nextpos.moveForward();
        if(isValidPosition(nextpos)){
            move = new ArrayList<Integer>(1);
            move.add(MOVE_FORWARD);
            return move;
        }
        System.out.println("right");
        nextpos = new RobotOrientation(this.robot);
        nextpos.turnRight();
        if(isValidPosition(nextpos)){
            move = new ArrayList<Integer>(1);
            move.add(TURN_RIGHT);
            return move;
        }
        return null;
    }

    public Robot getRobot() {
        return this.robot;
    }


    private boolean isValidPosition(RobotOrientation r){
        int posX = r.getPosX(), posY = r.getPosY();
        //System.out.println("validity check" + r.toPairFormat().toString());
        //out of arena
        if(posX<=0 || posX >= MAZE_HEIGHT || posY<=0 || posY >= MAZE_WIDTH) return false;
        //System.out.println("in arena");
        //maze position is blocked
        for(int i=0;i<=2;i++){
            for(int j=0;j<=2;j++){
                System.out.println((posX+i)+" "+(posY+j));
                if(mazeMap[posX+i][posY+j].isExplored() == false) return false;
                System.out.println("explored");
                if(mazeMap[posX+i][posY+j].isBlocked()) return false;
                System.out.println("not blocked");
            }
        }
        System.out.println("valid");
        return true;
    }

    public Cell[][] getMaze() {
        return mazeMap;
    }

}
