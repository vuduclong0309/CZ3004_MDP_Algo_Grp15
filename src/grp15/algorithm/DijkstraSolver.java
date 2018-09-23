package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import javafx.util.Pair;

import java.util.*;

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

    public ArrayList<Integer> getPathFromDistanceMap(HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> distanceMap, RobotOrientation s, RobotOrientation t){
        RobotOrientation start = new RobotOrientation(s), target = new RobotOrientation(t);
        System.out.println("#path " + start.toPairFormat().toString() + target.toPairFormat().toString());
        ArrayList<Integer> path = new ArrayList<Integer>();
        while(target.isEqual(start)==false){
            System.out.println("@"+target.toPairFormat().toString() + "goal" + start.toPairFormat().toString());
            int lastMove = distanceMap.get(target.toPairFormat()).getValue();
            System.out.println("@"+target.toPairFormat().toString()+"direct"+lastMove);
            path.add(lastMove);
            switch (lastMove) {
                case MOVE_FORWARD: //Move backward
                    target.turnRight();
                    target.turnRight();
                    target.moveForward();
                    target.turnRight();
                    target.turnRight();
                    break;
                case TURN_LEFT:
                    target.turnRight();
                    break;
                case TURN_RIGHT:
                    target.turnLeft();
                    break;
            }
        }
        Collections.reverse(path);
        return path;
    }

    public HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> getDistanceMap(){
        boolean st = true;
        HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> res = new HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>>();
        PriorityQueue<Pair<Integer, RobotOrientation>> q = new PriorityQueue<>(100, new PairComparator());
        RobotOrientation start = new RobotOrientation(robot);
        res.put(start.toPairFormat(), new Pair(0, STOP));
        q.add(new Pair(0, start));

        while(!q.isEmpty()){
            Pair<Integer, RobotOrientation> tmp = q.poll();
            int orientationValue = tmp.getKey();
            RobotOrientation pos = tmp.getValue(), nextPos;
            //System.out.println(pos.toPairFormat().toString());
            //Move forward
            //System.out.println("Try move forward");
            nextPos = new RobotOrientation(pos);
            //System.out.println("nextpos" + nextPos.getPosX() + nextPos.getPosY());
            nextPos.moveForward();
            //System.out.println("nextpos" + nextPos.toPairFormat().toString());
            if(isValidPosition(nextPos)) {
                //System.out.println("is valid position");
                if (!res.containsKey(nextPos.toPairFormat())) {
                    res.put(nextPos.toPairFormat(), new Pair(orientationValue + moveCost, MOVE_FORWARD));
                    //System.out.println("yolo2 "+pos.getPosX() + " " + pos.getPosY() + " " + res.get(nextPos.toPairFormat()).getKey() + " " + orientationValue + moveCost);
                    q.add(new Pair(orientationValue + moveCost, nextPos));
                } else if (res.get(nextPos.toPairFormat()).getKey() > orientationValue + moveCost) {
                    //System.out.println("yolo "+pos.getPosX() + " " + pos.getPosY() + " " + res.get(nextPos.toPairFormat()).getKey() + " " + orientationValue + moveCost);
                    res.replace(nextPos.toPairFormat(), new Pair(orientationValue + moveCost, MOVE_FORWARD));
                    q.add(new Pair(orientationValue + moveCost, nextPos));
                }
            }
            //Turn left
            nextPos = new RobotOrientation(pos);
            //System.out.println("pos" + pos.getPosX() + pos.getPosY());
            //System.out.println("nextpos" + nextPos.getPosX() + nextPos.getPosY());
            nextPos.turnLeft();
            //System.out.println("nextpos" + nextPos.getPosX() + nextPos.getPosY());
            if(isValidPosition(nextPos)) {
                //System.out.println("is valid position");
                if (!res.containsKey(nextPos.toPairFormat())) {
                    res.put(nextPos.toPairFormat(), new Pair(orientationValue + moveCost, TURN_LEFT));
                    q.add(new Pair(orientationValue + turnCost, nextPos));
                } else if (res.get(nextPos.toPairFormat()).getKey() > orientationValue + turnCost) {
                    //System.out.println("yolo "+pos.getPosX() + " " + pos.getPosY() + " " + res.get(nextPos.toPairFormat()).getKey() + " " + orientationValue + moveCost);
                    res.replace(nextPos.toPairFormat(), new Pair(orientationValue + turnCost, TURN_LEFT));
                    q.add(new Pair(orientationValue + turnCost, nextPos));
                }
            }
            //Turn right
            nextPos = new RobotOrientation(pos);
            //System.out.println("nextpos" + nextPos.getPosX() + nextPos.getPosY());
            nextPos.turnRight();
            //System.out.println("nextpos" + nextPos.getPosX() + nextPos.getPosY());
            if(isValidPosition(nextPos)) {
                //System.out.println("is valid position");
                if (!res.containsKey(nextPos.toPairFormat())) {
                    res.put(nextPos.toPairFormat(), new Pair(orientationValue + moveCost, TURN_RIGHT));
                    q.add(new Pair(orientationValue + turnCost, nextPos));
                } else if (res.get(nextPos.toPairFormat()).getKey() > orientationValue + turnCost) {
                    //System.out.println("yolo "+pos.getPosX() + " " + pos.getPosY() + " " + res.get(nextPos.toPairFormat()).getKey() + " " + orientationValue + moveCost);
                    res.replace(nextPos.toPairFormat(), new Pair(orientationValue + turnCost, TURN_RIGHT));
                    q.add(new Pair(orientationValue + turnCost, nextPos));
                }
            }
        }
        //System.out.println("done");

        return res;
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
                //System.out.println((posX+i)+" "+(posY+j));
                if(mazeMap[posX+i][posY+j].isExplored() == false) return false;
                if(mazeMap[posX+i][posY+j].isBlocked()) return false;
                //System.out.println("not blocked");
            }
        }
        //System.out.println("valid");
        return true;
    }

    public Cell[][] getMaze() {
        return mazeMap;
    }

}

class PairComparator implements Comparator<Pair<Integer, RobotOrientation>>{
    public int compare(Pair<Integer, RobotOrientation> p1, Pair<Integer, RobotOrientation> p2) {
        if (p1.getKey() < p2.getKey())
            return 1;
        else if (p1.getKey() > p2.getKey())
            return -1;
        return 0;
    }
}
