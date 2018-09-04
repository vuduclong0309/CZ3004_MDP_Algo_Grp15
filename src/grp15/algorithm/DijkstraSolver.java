package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.PriorityQueue;

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

    public HashMap<RobotOrientation, Integer> getDistanceMap(){
        HashMap<RobotOrientation, Integer> res = new HashMap<RobotOrientation, Integer>();
        PriorityQueue<Pair<Integer, RobotOrientation>> q = new PriorityQueue<>();
        RobotOrientation start = new RobotOrientation(robot);
        res.put(start, 0);
        q.add(new Pair(0, start));
        while(!q.isEmpty()){
            Pair<Integer, RobotOrientation> tmp = q.poll();
            int orientationValue = tmp.getKey();
            RobotOrientation pos = tmp.getValue(), nextPos;

            nextPos = pos.moveForward();

            if(!isValidPosition(nextPos)) continue;

            if(!res.containsKey(nextPos)){
                res.put(nextPos, orientationValue + moveCost);
                q.add(new Pair(orientationValue + moveCost, nextPos));
            }
            else if(res.get(nextPos) > orientationValue + moveCost){
                res.put(nextPos, orientationValue + moveCost);
                q.add(new Pair(orientationValue + moveCost, nextPos));
            }
        }
        return res;
    }

    private boolean isValidPosition(RobotOrientation r){
        int posX = r.getPosX(), posY = r.getPosY();
        if(posX<=0 || posX >= MAZE_WIDTH || posY<=0 || posY >= MAZE_HEIGHT) return false;
        if(!mazeMap[posX][posY].isExplored()) return false;

        //maze position is blocked
        for(int i=0;i<=2;i++){
            for(int j=0;j<=2;j++){
                if(mazeMap[posX+i][posY+j].isBlocked()) return false;
            }
        }
        return true;
    }
}
