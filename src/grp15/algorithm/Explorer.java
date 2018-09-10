package algorithm;

import grp15.algorithm.DijkstraSolver;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.simulator.MazeSolver;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(map.getRobot().getPosX());
        DijkstraSolver solver = new DijkstraSolver(map.getMazeCell(), 1, 1, this.map.getRobot());
        map.senseMap();
        this.map.repaint();
        int i = 0;
        do{
            i++;
            System.out.println(map.getRobot().getPosX());
            System.out.println(solver.getRobot().getPosX());
            HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> solution = solver.getDistanceMap();
            HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> nextPosMinDistance = null;
            for(HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> entry: solution.entrySet()){
                if (nextPosMinDistance == null || nextPosMinDistance.getValue().getKey() > entry.getValue().getKey()){
                    //System.out.println(nextPosMinDistance);
                    if(nextPosMinDistance == null) {
                        nextPosMinDistance = entry;
                    } else {
                        int nextPosX = nextPosMinDistance.getKey().getKey().getKey();
                        int nextPosY = nextPosMinDistance.getKey().getKey().getValue();
                        if (!map.getMazeCell()[nextPosX][nextPosY].isExplored())
                            nextPosMinDistance = entry;
                    }
                }
            }
            //System.out.println(solution.get(new RobotOrientation(robot).toPairFormat()));
            int nextMoveSignal = solution.get(new RobotOrientation(map.getRobot()).toPairFormat()).getValue();
            do{
                System.out.println("Move!");
                System.out.println(map.getRobot().getPosX() + " "+ map.getRobot().getPosY() + " " + nextMoveSignal);
                map.getRobot().moveRobot(nextMoveSignal);
                System.out.println(map.getRobot().getPosX() + " "+ map.getRobot().getPosY());
                map.senseMap();
                this.map.repaint();
                nextMoveSignal = solution.get(new RobotOrientation(map.getRobot()).toPairFormat()).getValue();
            } while(nextMoveSignal != Robot.STOP);
        }while(i<=400);
        System.out.println("finished");
    }
}
