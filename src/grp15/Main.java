package grp15;

import grp15.algorithm.Explorer;
import grp15.simulator.MazeEditor;
import grp15.object.Robot;
import grp15.simulator.MazeSolver;
import grp15.util.MapIOProcessor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import static grp15.object.Cell.GRID_SIZE;
import static grp15.simulator.MazeEditor.*;


final public class Main
{
    int WAYPOINT_X = Explorer.WAYPOINT_X;
    int WAYPOINT_Y = Explorer.WAYPOINT_Y;

    private static MazeEditor map;
    private static MazeSolver mapSolver;
    private static Robot bot;
    private static int time;
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setTitle("Map editor ");
        f.setSize(new Dimension(MAZE_WIDTH*GRID_SIZE,MAZE_HEIGHT*GRID_SIZE));
        f.setResizable(false);

        map = new MazeEditor();

        Container contentPane = f.getContentPane();
        contentPane.add(map, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JPanel textPanel = new JPanel();


        // explore
        JButton exploreButton = new JButton("Explore Maze");
        exploreButton.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                //bot = new MDPRobot(Integer.parseInt(sensorFSTxt.getText()),Integer.parseInt(sensorFLTxt.getText()),Integer.parseInt(sensorLSTxt.getText()),Integer.parseInt(sensorLLTxt.getText()),Integer.parseInt(sensorRSTxt.getText()),Integer.parseInt(sensorRLTxt.getText()));
                bot = new Robot(1,1,0);
                System.out.println("bot created!");
                mapSolver = new MazeSolver(map, bot);
                Explorer exp = new Explorer(mapSolver);
                System.out.println("Explorer created!");
                exp.launch();

            }

        });

        // timer

        JButton timeSolver = new JButton ("Timer");
        timeSolver.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Time in sec:",null);
                time=(Integer.parseInt(input)*1000);

                bot = new Robot(1,1,0);
                System.out.println("bot created!");
                mapSolver = new MazeSolver(map, bot);
                Explorer exp = new Explorer(mapSolver);
                System.out.println("Explorer created!");

                //the timer that is being used
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        exp.timeup();
                    }
                };
                Timer timer = new Timer( time / Explorer.SPEED, actionListener );
                timer.setRepeats(false);
                timer.start();
                exp.launch();
            }

        });

        JButton loadMap = new JButton ("Load Map");
        loadMap.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Select a map");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt","txt");
                jfc.addChoosableFileFilter(filter);

                int state = jfc.showOpenDialog(f);
                if (state == JFileChooser.APPROVE_OPTION)
                    MapIOProcessor.readMapFileFromDisk(jfc.getSelectedFile(),map);

            }

        });
        buttonPanel.add(loadMap);


        buttonPanel.add(exploreButton);
        buttonPanel.add(timeSolver);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(textPanel, BorderLayout.NORTH);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}