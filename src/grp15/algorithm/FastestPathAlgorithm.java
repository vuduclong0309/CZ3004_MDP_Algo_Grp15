package algorithm;

import grp15.simulator.MazeSolver;

public class FastestPathAlgorithm {
    private MazeSolver maze;
    int turnCost;
    int timeLimit;

    public FastestPathAlgorithm(MazeSolver m, int turnCost, int t){
        this.turnCost = turnCost;
        this.timeLimit = t;
        this.maze = m;
    }
}
