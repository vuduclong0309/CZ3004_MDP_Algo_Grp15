package algorithm;

import grp15.object.Robot;
import grp15.simulator.MazeSolver;

import javax.swing.*;
import java.awt.*;

import static grp15.object.Cell.GRID_SIZE;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;
import static grp15.simulator.MazeEditor.MAZE_HEIGHT;

public class Explorer {
    private Robot robot = null;
    private MazeSolver map = null;
    private JFrame frame;
    private boolean reachedGoal = false;
    private int time = 360;
    private boolean stopFlag = true;
    private boolean interrupted;

    public Explorer(Robot r, MazeSolver m) {
        this.robot = r;
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

            }
        }  );
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();

    }
}
