package grp15.algorithm;

import grp15.algorithm.DijkstraSolver;
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
    private MazeSolver map;
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
        DijkstraSolver solver = new DijkstraSolver(map.getMazeCell(), visited, 1, 1, this.map.getRobot());
        map.senseMap();
        this.map.repaint();
        int i = 0;
        do{
            System.out.println("iteration"+i);
            i++;
            HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> solution = solver.getDistanceMap();
            HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> nextPosMinDistance = null;
            for(HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> entry: solution.entrySet()){
                //System.out.println("entry"+entry.toString());
                if (nextPosMinDistance == null){
                    int nextPosX = entry.getKey().getKey().getKey();
                    int nextPosY = entry.getKey().getKey().getValue();
                    int direction = entry.getKey().getValue();
                    if (visited[nextPosX][nextPosY][direction] == false) {
                        nextPosMinDistance = entry;
                    }
                } else if (nextPosMinDistance.getValue().getKey() > entry.getValue().getKey()){
                    //System.out.println(nextPosMinDistance.toString());
                    int nextPosX = entry.getKey().getKey().getKey();
                    int nextPosY = entry.getKey().getKey().getValue();
                    int direction = entry.getKey().getValue();
                    if (visited[nextPosX][nextPosY][direction] == false)
                        nextPosMinDistance = entry;
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
                    visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()][k] = true;
                    visited[solver.getRobot().getPosX()+1][solver.getRobot().getPosY()][k] = true;
                    visited[solver.getRobot().getPosX()-1][solver.getRobot().getPosY()][k] = true;
                    visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()+1][k] = true;
                    visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()-1][k] = true;
                }
                try {
                    Thread.sleep(1000/2);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            //System.out.println("robot position" + solver.getRobot().getPosX() + solver.getRobot().getPosY() + solver.getRobot().getDirection());
        }while(true);
        FastestPathAlgorithm pathAlgorithm = new FastestPathAlgorithm(solver);
        pathAlgorithm.moveRobotToPosition(new RobotOrientation(new Pair(new Pair(1,1), 0)), map);
        pathAlgorithm.moveRobotToPosition(new RobotOrientation(new Pair(new Pair(MAZE_HEIGHT - 4, MAZE_WIDTH - 4), 0)), map);
        System.out.println("finished");
    }
}
