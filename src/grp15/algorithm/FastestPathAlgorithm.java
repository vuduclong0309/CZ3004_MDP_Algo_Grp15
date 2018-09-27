package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.CellColor;
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

    public void moveRobotToPosition(RobotOrientation target, MazeSolver mazeMap, boolean drawPath){
        ArrayList<Integer> movePath = solver.getPathFromDistanceMap(solver.getDistanceMap(), solver.getRobot().getOrientation(), target);
        for(int j = 0; j < movePath.size(); j++){
            if(drawPath == true){
                for(int k = 0; k < 3; k++)
                    for(int l = 0; l < 3; l++) {
                        int dx = solver.getRobot().getPosX()+k; int dy = solver.getRobot().getPosY()+l;
                        if(solver.getMaze()[dx][dy].getColor() == CellColor.FREE) solver.getMaze()[dx][dy].setColor(CellColor.SPATH);
                    }
            }
            System.out.println(solver.getRobot().getPosX() + " " + solver.getRobot().getPosY() + " " + movePath.get(j));
            mazeMap.getRobot().moveRobot(movePath.get(j));
            mazeMap.senseMap();
            try {
                Thread.sleep(1000/Explorer.SPEED);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mazeMap.repaint();
        }
    }
}