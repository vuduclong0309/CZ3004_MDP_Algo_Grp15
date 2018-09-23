package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static grp15.object.Cell.GRID_SIZE;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;

public class Explorer {
    static DijkstraSolver solver;
    private static MazeSolver map;
    private JFrame frame;
    private boolean reachedGoal = false;
    private int time = 360;
    private boolean stopFlag = true;
    private boolean interrupted;
    boolean visited [][][] = new boolean[MAZE_HEIGHT][MAZE_WIDTH][4];

    public Explorer(MazeSolver m) {
        this.map = m;
    }

    public void launch(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                frame = new JFrame("Explorer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(BorderLayout.CENTER, map);
                frame.setResizable(false);
                frame.setSize(new Dimension(MAZE_WIDTH * (GRID_SIZE+2), MAZE_HEIGHT * (GRID_SIZE+2)));
                //setting the window location
                frame.setLocationByPlatform(false);
                frame.setLocation(0, 0);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                startExploration();
            }
        }  );
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();

    }

    public void startExploration(){
        map.senseMap();
        this.map.repaint();
        int i = 0;
        boolean init = true;
        do{
            solver = new DijkstraSolver(map.getMazeCell(), 1, 1, this.map.getRobot());
            System.out.println("iteration"+i);
            i++;
            HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> solution = solver.getDistanceMap();
            HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> nextPosMinDistance = null;
            int gridIndex = 0;
            for(HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> entry: solution.entrySet()){
                //System.out.println("entry"+entry.toString());
                int nextPosX = entry.getKey().getKey().getKey();
                int nextPosY = entry.getKey().getKey().getValue();
                int direction = entry.getKey().getValue();
                int newIndex = falseSense(nextPosX, nextPosY, map.getMazeCell()); // == 0 && !(nextPosX == 1 && nextPosY == 1)) continue;
                //init = false;
                if (nextPosMinDistance == null){

                    if (visited[nextPosX][nextPosY][direction] == false) {
                        nextPosMinDistance = entry;
                        gridIndex = newIndex;
                    }
                } else if (gridIndex < newIndex){
                    //System.out.println(nextPosMinDistance.toString());

                    if (visited[nextPosX][nextPosY][direction] == false) {
                        nextPosMinDistance = entry;
                        gridIndex = newIndex;
                    }
                }
            }
            System.out.println("done entry");
            if(nextPosMinDistance == null){
                System.out.println("Exploration Completed");
                break;
            }
            //System.out.println(solution.get(new RobotOrientation(robot).toPairFormat()));
            ArrayList<Integer> path = solver.getPathFromDistanceMap(solution, new RobotOrientation(solver.getRobot()), new RobotOrientation(nextPosMinDistance.getKey()));
            visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()][solver.getRobot().getDirection()] = true;
            for(int j = 0; j < path.size(); j++){
                System.out.println(solver.getRobot().getPosX() + " " + solver.getRobot().getPosY() + " " + path.get(j));
                map.getRobot().moveRobot(path.get(j));
                map.senseMap();
                //for(int k=1;k<=100;k++) System.out.println("pause thread");
                this.map.repaint();
                for(int k = 0; k < 4; k++) {
                    visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()][solver.getRobot().getDirection()] = true;
                }
                try {
                    Thread.sleep(1000/10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(coverage((MAZE_HEIGHT) * (MAZE_WIDTH), map)) break;

            //System.out.println("robot position" + solver.getRobot().getPosX() + solver.getRobot().getPosY() + solver.getRobot().getDirection());
        }while(true);
        solver = new DijkstraSolver(map.getMazeCell(), 1, 1, this.map.getRobot());
        FastestPathAlgorithm pathAlgorithm = new FastestPathAlgorithm(solver);
        pathAlgorithm.moveRobotToPosition(new RobotOrientation(new Pair(new Pair(MAZE_HEIGHT - 4,1), 3)), map);
        pathAlgorithm.moveRobotToPosition(new RobotOrientation(new Pair(new Pair(1, MAZE_WIDTH - 4), 3)), map);
        System.out.println("finished");
    }

    int falseSense(int posX, int posY, Cell[][] maze){
        int res = 0;
        if(!maze[posX-1][posY].isExplored()) res++;
        if(!maze[posX-1][posY+1].isExplored()) res++;
        if(!maze[posX-1][posY+2].isExplored()) res++;
        if(!maze[posX+3][posY].isExplored()) res++;
        if(!maze[posX+3][posY+1].isExplored()) res++;
        if(!maze[posX+3][posY+2].isExplored()) res++;
        if(!maze[posX][posY-1].isExplored()) res++;
        if(!maze[posX+1][posY-1].isExplored()) res++;
        if(!maze[posX+2][posY-1].isExplored()) res++;
        if(!maze[posX][posY+3].isExplored()) res++;
        if(!maze[posX+1][posY+3].isExplored()) res++;
        if(!maze[posX+2][posY+3].isExplored()) res++;
        return res;
    }

    boolean coverage(int limit, MazeSolver maze){
        int cnt = 0;
        for(int i = 1; i < MAZE_HEIGHT; i++){
            for(int j = 1; j< MAZE_WIDTH; j++) if(maze.getMazeCell()[i][j].isExplored() == false) return false;
        }
        return true;
    }
}
