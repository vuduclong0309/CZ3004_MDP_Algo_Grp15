package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;

import java.util.ArrayList;

import static grp15.object.Robot.*;
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

    public ArrayList<Integer> getMove(MazeSolver map, RobotOrientation pos){
        ArrayList<Integer> move;
        RobotOrientation nextpos = new RobotOrientation(pos);
        System.out.println("analyzing" + nextpos.toPairFormat());
        System.out.println(nextpos.toPairFormat());
        nextpos.turnLeft();
        System.out.println(nextpos.toPairFormat());
        nextpos.moveForward();
        System.out.println(nextpos.toPairFormat());
        System.out.println("left");
        if(isValidPosition(nextpos, true) && !isDeadEnd(nextpos, map)) {
            move = new ArrayList<Integer>(2);
            move.add(TURN_LEFT);
            move.add(MOVE_FORWARD);
            return move;
        }
        System.out.println("forward");
        nextpos = new RobotOrientation(pos);
        nextpos.moveForward();
        if(isValidPosition(nextpos, true) && !isDeadEnd(nextpos, map)){
            move = new ArrayList<Integer>(1);
            move.add(MOVE_FORWARD);
            return move;
        }
        System.out.println("right");
        nextpos = new RobotOrientation(pos);
        nextpos.turnRight();
        move = new ArrayList<Integer>(1);
        move.add(TURN_RIGHT);
        return move;
    }

    public ArrayList<Integer> getBurstMove(MazeSolver map, RobotOrientation pos){
        ArrayList<Integer> burstMove = new ArrayList<Integer>();
        ArrayList<Integer> nextMove;
        //System.out.println("burst move start pos" + pos.toPairFormat().toString());
        do {
            nextMove = getMove(map, pos);
            //System.out.println("virtual run pos before" + pos.toPairFormat().toString());
            //System.out.println("next move" + FastestPathAlgorithm.movePathToSignalString(nextMove));
            burstMove.addAll(nextMove);
            for(int i = 0; i < nextMove.size(); i++){
                System.out.println(nextMove.get(i));
                pos.moveOrientation(nextMove.get(i));
            }
            //System.out.println("virtual run pos after" + pos.toPairFormat().toString());
            if(pos.isEqual(new RobotOrientation(1, 1, 3))) break;
        }while(pos.falseSenseSensorData(map) == 0);
        return burstMove;
    }

    public Robot getRobot() {
        return this.robot;
    }


    private boolean isValidPosition(RobotOrientation r, boolean exploredConstraint){
        int posX = r.getPosX(), posY = r.getPosY();
        //System.out.println("validity check" + r.toPairFormat().toString());
        //out of arena
        if(posX<=0 || posX >= MAZE_HEIGHT || posY<=0 || posY >= MAZE_WIDTH) return false;
        //System.out.println("in arena");
        //maze position is blocked
        for(int i=0;i<=2;i++){
            for(int j=0;j<=2;j++){
                //System.out.println((posX+i)+" "+(posY+j));
                if(mazeMap[posX+i][posY+j].isExplored() == false) {
                    if(exploredConstraint == false) continue;
                    else return false;
                }
                //System.out.println("explored");
                if(mazeMap[posX+i][posY+j].isBlocked()) return false;
                //
                //
                // System.out.println("not blocked");
            }
        }
        //System.out.println("valid");
        return true;
    }

    private boolean isDeadEnd(RobotOrientation pos, MazeSolver map){
        if(pos.falseSenseSensorData(map) != 0) return false;
        //System.out.println("isDeadend" + pos.toPairFormat().toString());
        RobotOrientation moveLeft = new RobotOrientation(pos).turnLeft().moveForward(),
                moveForward = new RobotOrientation(pos).moveForward(),
                moveRight = new RobotOrientation(pos).turnRight().moveForward();
        if(map.getCell(moveLeft.getPosX(), moveLeft.getPosY()).isStart() ||
                map.getCell(moveForward.getPosX(), moveForward.getPosY()).isStart() ||
                map.getCell(moveRight.getPosX(), moveRight.getPosY()).isStart()) return false;
        //System.out.println(isValidPosition(moveLeft, false) + " " + isValidPosition(moveForward, false) + " " + isValidPosition(moveRight, false));
        return !(isValidPosition(moveLeft, false) || isValidPosition(moveForward, false) || isValidPosition(moveRight, false));
    }

    public Cell[][] getMaze() {
        return mazeMap;
    }

}