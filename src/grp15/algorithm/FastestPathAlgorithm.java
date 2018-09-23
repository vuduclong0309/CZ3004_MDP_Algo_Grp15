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
            mazeMap.repaint();
        }
    }
}
