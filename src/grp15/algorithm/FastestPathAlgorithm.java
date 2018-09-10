package algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import java.util.HashMap;

public class FastestPathAlgorithm {
    private MazeSolver maze;
    int turnCost, moveCost;
    int timeLimit;

    public FastestPathAlgorithm(MazeSolver m, int turnCost, int moveCost, int t){
        this.turnCost = turnCost;
        this.moveCost = moveCost;
        this.timeLimit = t;
        this.maze = m;
    }

    public void solveShortestPath(){
        Robot bot = maze.getRobot();
        DijkstraSolver solver = new DijkstraSolver(maze.getMazeCell(), this.turnCost, this.moveCost, bot);
        HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> solution = solver.getDistanceMap();
        while(solution.get(new RobotOrientation(bot).toPairFormat()).getValue()!=Robot.STOP){
            int nextMoveSignal = solution.get(new RobotOrientation(bot)).getValue();
            bot.moveRobot(nextMoveSignal);
            System.out.println("Move " + nextMoveSignal);
        }
    }
}
