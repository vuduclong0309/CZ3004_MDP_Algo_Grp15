package simulator;


import static util.MapIOProcessor.readFile;

import robot.Robot;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Simulator {
	private static final boolean PHYSICALRUN = false;
	
	private static Robot robot;
	
	private static Map actualMap = null;
	private static Map exploredMap = null;
	
	private static JFrame simulatorFrame = null;
	private static JPanel mapPanel = null;
	private static JPanel buttonPanel = null;

	public static void main(String[] args) {
		/*if (PHYSICALRUN)
			com.openConnection();*/
		robot = new Robot();
		
		if (!PHYSICALRUN) {
			actualMap = new Map(robot);
			actualMap.setAllUnexplored();
		}
			
		launchSimulator();
		
	}
	private static void launchSimulator() {
		
		//Set up JFrame and its size
		simulatorFrame = new JFrame();
		simulatorFrame.setTitle("CZ3004 MDP AY18/19S1 Group 15 Simulator");
		simulatorFrame.setSize(620, 780);
		simulatorFrame.setLocationRelativeTo(null);
		simulatorFrame.setVisible(true);
		
		//create new JPanel Objects
		mapPanel = new JPanel(new CardLayout());
		buttonPanel = new JPanel(new FlowLayout());
		
		if (!PHYSICALRUN) {
			mapPanel.add("ACTUAL_MAP", actualMap);
			virtualRun();
		}
		/*else
			physicalRun();*/
		//create buttons and add into JPanel
		JButton btnExploration = new JButton("Exploration");
		JButton btnFastestPath = new JButton("Fastest Path");
		buttonPanel.add(btnExploration);
		buttonPanel.add(btnFastestPath);
		
		// Put the two JPanel into JFrame
		simulatorFrame.getContentPane().add(mapPanel, BorderLayout.CENTER);
		simulatorFrame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		
		
	}
	
	private static void virtualRun() {
		/*
		 *  Create Jbutton to choose map to load into the simulator.
		 */
		JButton btnLoadMap = new JButton("Load Arena Map");
		
		/*
		 *  Launch dialog to input map file.
		 */
		btnLoadMap.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JDialog dlLoadMap = new JDialog(simulatorFrame, "Load Map", true);
				dlLoadMap.setSize(400, 100);
				dlLoadMap.setLayout(new FlowLayout());

                final JTextField tbMapName = new JTextField(15);
                JButton btnLoad = new JButton("Load");
               
                
                btnLoad.addMouseListener(new MouseAdapter() {
                	public void mousePressed(MouseEvent e) {
                		dlLoadMap.setVisible(false);
                		readFile(tbMapName.getText(),actualMap);
                		CardLayout cl = ((CardLayout) mapPanel.getLayout());
                		cl.show(mapPanel, "ACTUAL_MAP");
                		actualMap.repaint();
                		
                	}
                	
                	
                });
               
                dlLoadMap.add(new JLabel("File Name: "));
                dlLoadMap.add(tbMapName);
                dlLoadMap.add(btnLoad);
                dlLoadMap.setLocationRelativeTo(simulatorFrame);
                dlLoadMap.setVisible(true);
			}
			
		});
		buttonPanel.add(btnLoadMap);
		
		
	}
}

