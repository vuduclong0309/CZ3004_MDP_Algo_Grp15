/**
 *      created by vuduclong0309
 */
package grp15.algorithm;

import grp15.object.Cell;
import grp15.object.Robot;
import grp15.object.RobotOrientation;
import grp15.rpi.Comms;
import grp15.simulator.MazeSolver;
import grp15.util.MapDescriptor;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static grp15.Main.communicator;
import static grp15.object.Cell.GRID_SIZE;
import static grp15.object.CellColor.FREE;
import static grp15.object.CellColor.WAYPOINT;
import static grp15.object.Robot.*;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;

public class Explorer {

    public static String finalMapAndroid = "";
    public double coverageThreshold = 0.5;
    public static int WAYPOINT_X = 3;
    public static int WAYPOINT_Y = 11;
    public static int SPEED = 1000000;
    static DijkstraSolver solver;
    private MazeSolver map;
    boolean startFP = false;
    private JFrame frame;

    boolean visited [][][] = new boolean[MAZE_HEIGHT][MAZE_WIDTH][4];
    private boolean timeout = false;

    public Explorer(MazeSolver m) {
        this.map = m;
    }

    public void launch(){
        //Creating MazeSolver GUI window
        Thread thread = new Thread(new Runnable() {
            public void run() {
                frame = new JFrame("Explorer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(BorderLayout.CENTER, map);
                frame.setResizable(false);
                frame.setSize(new Dimension(MAZE_WIDTH * (GRID_SIZE+1), MAZE_HEIGHT * (GRID_SIZE+3)));
                JButton startFastestPathButton = new JButton ("Start Fastest Path");
                startFastestPathButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        startFP = true;
                    }

                });

                frame.getContentPane().add(startFastestPathButton, BorderLayout.SOUTH);
                //setting the window location
                frame.setLocationByPlatform(false);
                frame.setLocation(0, 0);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //Receive signal from Android side, code "START_EXPLORE ROBOT_POS_X ROBOT_POS_Y"
                while(true){
                    String msg = communicator.recvMsg();
                    String msgArr[] = msg.split(" ");
                    if (msgArr[0].equals(Comms.START_EXPLORE)){
                        switch (msgArr[3]){
                            case "NORTH":
                                map.getRobot().setPos(Integer.parseInt(msgArr[1]), Integer.parseInt(msgArr[2]), Robot.NORTH, map);
                                break;
                            case "SOUTH":
                                map.getRobot().setPos(Integer.parseInt(msgArr[1]), Integer.parseInt(msgArr[2]), Robot.SOUTH, map);
                                break;
                            case "EAST":
                                map.getRobot().setPos(Integer.parseInt(msgArr[1]), Integer.parseInt(msgArr[2]), Robot.EAST, map);
                                break;
                            case "WEST":
                                map.getRobot().setPos(Integer.parseInt(msgArr[1]), Integer.parseInt(msgArr[2]), Robot.WEST, map);
                                break;
                        }
                        break;
                    }
                }
                startExploration();

                //After exploration done, the robot halt to sense the medium for professor to capture exploration result
                while(startFP == false) {
                    try {
                        System.out.println("waiting for fastest path button");
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (Exception e){

                    }
                };

                //Receive fastest path signal, code "START_FASTEST_PATH WAYPOINT_X WAYPOINT_Y"
                //Waypoint is a requirement position that robot have to go through during fastest path
                while(true){
                    String msg = communicator.recvMsg();
                    System.out.println("test1");
                    String msgArr[] = msg.split(" ");
                    if (msgArr[0].equals(Comms.START_FASTEST_PATH)){
                        setWayPoint(Integer.parseInt(msgArr[2]) + 1, Integer.parseInt(msgArr[1]) + 1);
                        break;
                    }
                    else continue;
                }

                startFastestPath();

            }
        }  );
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();

    }

    public void startExploration(){
        //Send acknowledgement to robot
        communicator.sendMsg("o", "");

        //Initialization
        map.senseMap();
        MapDescriptor.generateMapDescriptor(map);
        this.map.repaint();
        int i = 0;
        boolean init = true;
        HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> distanceMap;
        solver = new DijkstraSolver(map.getMazeCell(), TURN_COST, MOVE_COST, this.map.getRobot());
        FastestPathAlgorithm pathAlgorithm = new FastestPathAlgorithm(solver);
        LeftWallHuggingSolver wallHuggingSolver = new LeftWallHuggingSolver(map.getMazeCell(), this.map.getRobot());

        //Single move mode
        /*do{
            ArrayList<Integer> path = wallHuggingSolver.getMove(map, new RobotOrientation(map.getRobot()));
            for(int j = 0; j < path.size(); j++){
                visited[wallHuggingSolver.getRobot().getPosX()][wallHuggingSolver.getRobot().getPosY()][wallHuggingSolver.getRobot().getDirection()] = true;
                System.out.println(wallHuggingSolver.getRobot().getPosX() + " " + wallHuggingSolver.getRobot().getPosY() + " " + path.get(j));
                map.getRobot().moveRobot(path.get(j));
                map.senseMap();
                MapDescriptor.generateMapDescriptor(map);
                //for(int k=1;k<=100;k++) System.out.println("pause thread");
                this.map.repaint();
                try {
                    Thread.sleep(1000/SPEED);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }while(!map.getRobot().getOrientation().isEqual(new RobotOrientation(1, 1, 3)));

        //End of single mode
        */
        //Wall Hugging Burst Mode
        do{
            ArrayList<Integer> path = wallHuggingSolver.getBurstMove(map, new RobotOrientation(map.getRobot()));
            if(path.size() > 2) { //If number of move > 2, send fast instruction string format to robot
                path.add(-1); //conclude string with letter 'o'

                //For monitoring purpose
                String signal = FastestPathAlgorithm.movePathToSignalString(path, true);
                System.out.println("Signal String: " + signal);

                pathAlgorithm.moveRobotbyPath(path, map, false, false, true);
                map.senseMap();
            }
            else { //If number of move <= 2, use slow instruction string format
                for(int j = 0; j < path.size(); j++){
                    //Mark visited position for Dijkstra/A* round
                    visited[wallHuggingSolver.getRobot().getPosX()][wallHuggingSolver.getRobot().getPosY()][wallHuggingSolver.getRobot().getDirection()] = true;

                    System.out.println(wallHuggingSolver.getRobot().getPosX() + " " + wallHuggingSolver.getRobot().getPosY() + " " + path.get(j));

                    //Update map
                    map.getRobot().moveRobot(path.get(j));
                    map.senseMap();
                    MapDescriptor.generateMapDescriptor(map);
                    this.map.repaint();

                    //Hold thread for 1 second to prevent RPI breakdown from its bug
                    try {
                        Thread.sleep(1000/SPEED);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }while(!map.getRobot().getOrientation().isEqual(new RobotOrientation(1, 1, 3))); //Ending position as agreed with hardware team

        //End of burst mode

        System.out.println("wall hug done");

        System.out.println("doing A*");
        System.out.println(map.coverage());
        //Dijksra/A* round
        /*
            This algorithm introduce priority index to Dijkstra in a fashion that similar to A*.
            However, the greedy function is:
                F(x): H(x) ^ m / G(x) ^ n, where:
                H(x): number of newly discovered cell at position x
                G(x): number of robot step to travel to position x
            By trial and error, I've selected m = 2 and n = 4 for best performance
            Robot will move to position with highest F(x) for maximal new discovered cell with minimum moving cost
         */
        do{
            System.out.println("iteration"+i);
            i++;
            distanceMap = solver.getDistanceMap();
            HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> nextPosMinDistance = null;
            double gridIndex = 0;
            for(HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> entry: distanceMap.entrySet()){

                int nextPosX = entry.getKey().getKey().getKey();
                int nextPosY = entry.getKey().getKey().getValue();
                int direction = entry.getKey().getValue();
                int distance = entry.getValue().getKey();
                int newIndex = falseSense(nextPosX, nextPosY, direction, map.getMazeCell()); // == 0 && !(nextPosX == 1 && nextPosY == 1)) continue;
                //init = false;
                System.out.println("entry"+entry.toString() + "@" + newIndex);
                if(newIndex == 0) continue;
                if (nextPosMinDistance == null){

                    if (visited[nextPosX][nextPosY][direction] == false) {
                        nextPosMinDistance = entry;
                        gridIndex = (double)(newIndex*newIndex)/(distance*distance*distance*distance);
                    }
                } else if (gridIndex < (double)(newIndex*newIndex)/(distance*distance*distance*distance)){
                    //System.out.println(nextPosMinDistance.toString());

                    if (visited[nextPosX][nextPosY][direction] == false) {
                        nextPosMinDistance = entry;
                        gridIndex = (double)(newIndex*newIndex)/(distance*distance*distance*distance);
                    }
                }
            }
            System.out.println("done entry");
            if(nextPosMinDistance == null){ //No more position that can update map
                System.out.println("Exploration Completed");
                break;
            }
            //System.out.println(solution.get(new RobotOrientation(robot).toPairFormat()));

            //Move robot using fast mode robot instruction
            ArrayList<Integer> path = solver.getPathFromDistanceMap(distanceMap, new RobotOrientation(solver.getRobot()), new RobotOrientation(nextPosMinDistance.getKey()));
            path.add(-1); //Conclude string with o
            String signal = FastestPathAlgorithm.movePathToSignalString(path, true);
            System.out.println("Signal String: " + signal);
            pathAlgorithm.moveRobotbyPath(path, map, false, false, true);
            map.senseMap();
            visited[solver.getRobot().getPosX()][solver.getRobot().getPosY()][solver.getRobot().getDirection()] = true;

            //System.out.println("robot position" + solver.getRobot().getPosX() + solver.getRobot().getPosY() + solver.getRobot().getDirection());
        }while(timeout == false);

        //Move robot back to start zone after exploration is complete
        ArrayList<Integer> backToStart = pathAlgorithm.getFastestPath(new RobotOrientation(map.getRobot()), new RobotOrientation(new Pair(new Pair(1, 1), WEST)));
        pathAlgorithm.moveRobotbyPath(backToStart, map, false, true, false);
        //String mapUpdate = MapDescriptor.toAndroid(map);
        map.getRobot().setPos(1, 1, NORTH, map);
        map.repaint();

        //Send completion confirmation for hardware team
        communicator.sendMsg("f", Comms.INSTRUCTIONS);

        //For RPI team
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){

        }

        //Final MDP String for grading purpose
        String [] finalMap = MapDescriptor.generateMapDescriptor(map);
        finalMapAndroid = MapDescriptor.toAndroid(map);

        communicator.sendMsg (finalMap[0] + " " + finalMap[1], Comms.FINAL_MAP);
        //communicator.sendMsg(mapUpdate + " " +this.getDirection() + " " + this.getPosX() + " " + this.getPosY(), Comms.MAP_STRINGS);
    }

    void startFastestPath(){
        //Initialization
        ArrayList<Integer> finalPath = new ArrayList<Integer>();
        FastestPathAlgorithm pathAlgorithm = new FastestPathAlgorithm(solver);

        if(map.getMazeCell()[WAYPOINT_X][WAYPOINT_Y].isExplored() == true) { //Waypoint is accessible
            HashMap<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> distanceMap;
            distanceMap = solver.getDistanceMap();

            //Finding best robot position that make robot touch the waypoint
            //As a requirement, we minimize the distance between the waypoint and robot centre (best case is that robot center is over the waypoint.
            HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> nextPosMinDistance = null;
            int minDistance = 10000;
            int minWaypointDislocation = 100;
            for (HashMap.Entry<Pair<Pair<Integer, Integer>, Integer>, Pair<Integer, Integer>> entry : distanceMap.entrySet()) {
                //System.out.println("entry"+entry.toString());
                int nextPosX = entry.getKey().getKey().getKey();
                int nextPosY = entry.getKey().getKey().getValue();
                int direction = entry.getKey().getValue();
                int distance = entry.getValue().getKey();
                int dx = nextPosX - WAYPOINT_X;
                int dy = nextPosY - WAYPOINT_Y;
                int waypointDislocation = Math.abs(dx + 1) + Math.abs(dy + 1);
                if (waypointDislocation > minWaypointDislocation) continue;
                if (waypointDislocation < minWaypointDislocation || minDistance > distance) {
                    //System.out.println(nextPosMinDistance.toString());
                    nextPosMinDistance = entry;
                    minDistance = distance;
                    minWaypointDislocation = waypointDislocation;
                }
            }
            ArrayList<Integer> startToWaypoint = pathAlgorithm.getFastestPath(new RobotOrientation(map.getRobot()), new RobotOrientation(nextPosMinDistance.getKey()));
            ArrayList<Integer> waypointToFinal = pathAlgorithm.getFastestPath(new RobotOrientation(nextPosMinDistance.getKey()), new RobotOrientation(new Pair(new Pair(MAZE_HEIGHT - 4, MAZE_WIDTH - 4), 0)));
            finalPath.addAll(startToWaypoint);
            finalPath.addAll(waypointToFinal);
        }
        else { //Waypoint is not accessible
            finalPath = pathAlgorithm.getFastestPath(new RobotOrientation(map.getRobot()), new RobotOrientation(new Pair(new Pair(MAZE_HEIGHT - 4, MAZE_WIDTH - 4), 0)));
        }
        pathAlgorithm.moveRobotbyPath(finalPath, map, true, true, false);
        System.out.println("finished");

    }

    //Evaluate position's priority index for Dijksra/A* traversal, which is the number of cell that can be disovered in given robot position
    int falseSense(int posX, int posY, int direction, Cell[][] maze){
        int res = 0;
        /*if(!maze[posX-1][posY].isExplored()) res++;
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
        if(!maze[posX+2][posY+3].isExplored()) res++;*/
        res = new RobotOrientation(posX, posY, direction).falseSenseSensorData(map);
        return res;
    }

    //Set sleep period between each robot command instruction if needed. The holding period is 1000ms/SPEED
    //Speed is set to 1000000 as moving delay is not neeeded in final version
    public void setSpeed(int value){
        this.SPEED = value;
    }

    //Exploration time is expired
    public void timeup(){
        this.timeout = true;
    }

    //Range is 0 (empty map) to 1 (complete map) robot will go back to start zone if the discovered position is higher than this
    public void setCoverageThreshold(double input){
        this.coverageThreshold = input;
    }

    //Set the waypoint
    public void setWayPoint(int wx, int wy){
        map.getMazeCell()[WAYPOINT_X][WAYPOINT_Y].setColor(FREE);
        this.WAYPOINT_X = wx;
        this.WAYPOINT_Y = wy;
        map.getMazeCell()[WAYPOINT_X][WAYPOINT_Y].setColor(WAYPOINT);
        map.repaint();
    }
}