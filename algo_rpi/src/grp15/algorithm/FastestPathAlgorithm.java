package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.CellColor;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.rpi.Comms;
import grp15.simulator.MazeSolver;
import grp15.util.MapDescriptor;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static grp15.Main.communicator;
import static grp15.object.Robot.*;

public class FastestPathAlgorithm {
    private DijkstraSolver solver;

    public FastestPathAlgorithm(DijkstraSolver s){
        this.solver = s;
    }

    public void moveRobotToPosition(RobotOrientation target, MazeSolver mazeMap, boolean drawPath){
        ArrayList<Integer> movePath = solver.getPathFromDistanceMap(solver.getDistanceMap(), solver.getRobot().getOrientation(), target);
        String res = movePathToSignalString(movePath);

        System.out.println("Move path strong code: " +res);
        communicator.sendMsg(res, Comms.INSTRUCTIONS);
        for(int j = 0; j < movePath.size(); j++){
            if(drawPath == true){
                for(int k = 0; k < 3; k++)
                    for(int l = 0; l < 3; l++) {
                        int dx = solver.getRobot().getPosX()+k; int dy = solver.getRobot().getPosY()+l;
                        //if(solver.getMaze()[dx][dy].getColor() == CellColor.FREE) solver.getMaze()[dx][dy].setColor(CellColor.SPATH);
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
            updateRobotPosition();
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

    public void updateRobotPosition(){
        communicator.sendMsg(Explorer.finalMapAndroid + " " + toDirectionString(solver.getRobot().getDirection()) + " " + solver.getRobot().getPosY() + " " + solver.getRobot().getPosX(), Comms.MAP_STRINGS);
        try{
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (Exception e){

        }
    }
}