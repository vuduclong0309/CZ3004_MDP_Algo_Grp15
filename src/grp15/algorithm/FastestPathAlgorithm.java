package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import static grp15.object.Robot.*;

public class FastestPathAlgorithm {
    private DijkstraSolver solver;

    public FastestPathAlgorithm(DijkstraSolver s){
        this.solver = s;
    }

    public void moveRobotToPosition(RobotOrientation target, MazeSolver mazeMap){
        ArrayList<Integer> movePath = solver.getPathFromDistanceMap(solver.getDistanceMap(), solver.getRobot().getOrientation(), target);
        solver.getVisited()[solver.getRobot().getPosX()][solver.getRobot().getPosY()][solver.getRobot().getDirection()] = true;
        for(int j = 0; j < movePath.size(); j++){
            System.out.println(solver.getRobot().getPosX() + " " + solver.getRobot().getPosY() + " " + movePath.get(j));
            mazeMap.getRobot().moveRobot(movePath.get(j));
            mazeMap.senseMap();
            try {
                Thread.sleep(1000/2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for(int k=1;k<=100;k++) System.out.println("pause thread");
            mazeMap.repaint();
            for(int k = 0; k < 4; k++) solver.getVisited()[solver.getRobot().getPosX()][solver.getRobot().getPosY()][k] = true;
        }
    }
}
