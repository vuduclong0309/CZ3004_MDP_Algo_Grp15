package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.CellColor;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static grp15.object.Robot.*;

public class FastestPathAlgorithm {
    private DijkstraSolver solver;

    public FastestPathAlgorithm(DijkstraSolver s){
        this.solver = s;
    }

    public ArrayList<Integer> getFastestPath(RobotOrientation source, RobotOrientation target){
        RobotOrientation originalPos = new RobotOrientation(solver.getRobot());
        solver.getRobot().setPosRaw(source);
        HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> distanceMap = solver.getDistanceMap();
        ArrayList<Integer> res = solver.getPathFromDistanceMap(distanceMap, source, target);
        solver.getRobot().setPosRaw(originalPos);
        return res;
    }

    public void moveRobotbyPath(ArrayList<Integer> movePath, MazeSolver mazeMap, boolean drawPath){
        String res = movePathToSignalString(movePath);
        res = pConvert(res, 2);
        System.out.println("Move path strong code: " +res);
        for(int j = 0; j < movePath.size(); j++){
            if(drawPath == true){
                for(int k = 0; k < 3; k++)
                    for(int l = 0; l < 3; l++) {
                        int dx = solver.getRobot().getPosX()+k; int dy = solver.getRobot().getPosY()+l;
                        if(solver.getMaze()[dx][dy].getColor() == CellColor.FREE) solver.getMaze()[dx][dy].setColor(CellColor.SPATH);
                    }
            }
            System.out.println(solver.getRobot().getPosX() + " " + solver.getRobot().getPosY() + " " + movePath.get(j));

            RobotOrientation tmp = new RobotOrientation(mazeMap.getRobot());
            switch (movePath.get(j)){
                case MOVE_FORWARD:
                    tmp.moveForward();
                    break;
                case TURN_LEFT:
                    tmp.turnLeft();
                    break;
                case TURN_RIGHT:
                    tmp.turnRight();
                    break;
            }
            solver.getRobot().setPosRaw(tmp.getPosX(), tmp.getPosY(), tmp.getDirection());

            try {
                Thread.sleep(1000/Explorer.SPEED);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazeMap.repaint();
        }
    }

    public static String movePathToSignalString(ArrayList<Integer> movePath){
        String res = "";
        int num = 0;
        for(int j = 0; j < movePath.size();j++) {
            int nextSignal = movePath.get(j);
            if (nextSignal == MOVE_FORWARD) num++;
            else {
                if(num != 0) {
                    res = res + forwardNumtoString(num);
                    num = 0;
                }
                switch (nextSignal) {
                    case TURN_LEFT:
                        res = res + "j";
                        break;
                    case TURN_RIGHT:
                        res = res + "l";
                        break;
                }
            }
        }
        if(num!=0) res = res + forwardNumtoString(num);
        return res;
    }

    public static String forwardNumtoString(int num){
        String res = "";
        if(num <= 9) res += num;
        else switch (num){
            case 10:
                res+= "0";
                break;
            case 11:
                res+="A";
                break;
            case 12:
                res+="B";
                break;
            case 13:
                res+="C";
                break;
            case 14:
                res+="D";
                break;
            case 15:
                res+="E";
                break;
            case 16:
                res+="F";
                break;
            case 17:
                res+="G";
                break;
            case 18:
                res+="H";
                break;
            case 19:
                res+="I";
                break;
            case 20:
                res+="J";
                break;
        }
        return res;
    }

    public static String pConvert(String start, int num){
        char tmp[] = new char[start.length()];
        for(int i = 0; i<start.length(); i++){
            tmp[i] = start.charAt(i);
        }

        for(int i = start.length() - 1; i>=0; i--){
            if(num == 0) break;
            if(tmp[i] == 'l' || tmp[i] == 'r') continue;
            tmp[i] = 'P';
            num--;
        }
        String res = "";
        for(int i = 0; i<start.length(); i++){
            res+=tmp[i];
        }
        return res;
    }
}