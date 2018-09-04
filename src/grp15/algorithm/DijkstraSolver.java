package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.PriorityQueue;

import static grp15.object.Robot.*;
import static grp15.simulator.MazeEditor.*;

public class DijkstraSolver {
    Cell[][] mazeMap;
    Robot robot;
    int turnCost, moveCost;
    public DijkstraSolver(Cell[][] maze, int c, int m, Robot r){
        this.mazeMap = maze;
        this.turnCost = c;
        this.moveCost = m;
        this.robot = r;
    };

    public  HashMap<RobotOrientation, Pair<Integer, Integer>> getDistanceMap(){
        HashMap<RobotOrientation, Pair<Integer, Integer>> res = new  HashMap<RobotOrientation, Pair<Integer, Integer>>();
        PriorityQueue<Pair<Integer, RobotOrientation>> q = new PriorityQueue<>();
        RobotOrientation start = new RobotOrientation(robot);
        res.put(start, new Pair(0, STOP));
        q.add(new Pair(0, start));
        while(!q.isEmpty()){
            Pair<Integer, RobotOrientation> tmp = q.poll();
            int orientationValue = tmp.getKey();
            RobotOrientation pos = tmp.getValue(), nextPos;

            //Move forward
            nextPos = pos.moveForward();

            if(!isValidPosition(nextPos)) continue;

            if(!res.containsKey(nextPos)){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, MOVE_FORWARD));
                q.add(new Pair(orientationValue + moveCost, nextPos));
            }
            else if(res.get(nextPos).getKey() > orientationValue + moveCost){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, MOVE_FORWARD));
                q.add(new Pair(orientationValue + moveCost, nextPos));
            }

            //Move left
            nextPos = pos.turnLeft();

            if(!isValidPosition(nextPos)) continue;

            if(!res.containsKey(nextPos)){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, TURN_LEFT));
                q.add(new Pair(orientationValue + turnCost, nextPos));
            }
            else if(res.get(nextPos).getKey() > orientationValue + turnCost * 2){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, TURN_LEFT));
                q.add(new Pair(orientationValue + turnCost, nextPos));
            }

            //Move right
            nextPos = pos.turnRight();

            if(!isValidPosition(nextPos)) continue;

            if(!res.containsKey(nextPos)){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, TURN_RIGHT));
                q.add(new Pair(orientationValue + turnCost, nextPos));
            }
            else if(res.get(nextPos).getKey() > orientationValue + turnCost * 2){
                res.put(nextPos, new Pair(orientationValue + moveCost, STOP));
                res.put(pos, new Pair(orientationValue, TURN_RIGHT));
                q.add(new Pair(orientationValue + turnCost, nextPos));
            }
        }
        return res;
    }

    private boolean isValidPosition(RobotOrientation r){
        int posX = r.getPosX(), posY = r.getPosY();

        //out of arena
        if(posX<=0 || posX >= MAZE_WIDTH || posY<=0 || posY >= MAZE_HEIGHT) return false;

        //maze position is blocked
        for(int i=0;i<=2;i++){
            for(int j=0;j<=2;j++){
                if(mazeMap[posX+i][posY+j].isBlocked()) return false;
            }
        }
        return true;
    }
}
