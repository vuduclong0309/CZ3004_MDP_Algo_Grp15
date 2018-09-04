package grp15;

import grp15.simulator.MazeEditor;
import grp15.object.Robot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


final public class Main
{
    private static MazeEditor map;
    private static Robot bot;
    private static int time;
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setTitle("Map editor ");
        f.setSize(new Dimension(700,800));
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

            }

        });

        // timer

        JButton timeSolver = new JButton ("Timer");
        timeSolver.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

            }

        });

        buttonPanel.add(exploreButton);
        buttonPanel.add(timeSolver);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(textPanel, BorderLayout.NORTH);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
