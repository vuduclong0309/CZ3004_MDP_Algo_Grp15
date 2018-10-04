package grp15.util;

import grp15.simulator.MazeSolver;
import grp15.simulator.MazeEditor;
import grp15.simulator.MazeSolver;

import static grp15.simulator.MazeEditor.*;

public class MapDescriptor {
	
	public static String[] generateMapDescriptor(MazeSolver arenaMap) {
        String[] mapDescriptors = new String[2];

        StringBuilder part1Hex = new StringBuilder();
        StringBuilder part1Bin = new StringBuilder();
        String strP1Hex = "";
        part1Bin.append("11");
        for (int row = 1; row < MAZE_HEIGHT - 1; row++) {
            for (int col = 1; col < MAZE_WIDTH - 1; col++) {
                if (arenaMap.getCell(row, col).isExplored())
                    part1Bin.append("1");
                else
                    part1Bin.append("0");

            }
        }
        part1Bin.append("11");
        for (int i = part1Bin.length() - 1; i >= 0 ;i--) {
            part1Hex.insert(0, part1Bin.charAt(i));
            if (part1Hex.length() == 4) {
                strP1Hex = binToHex(part1Hex.toString()) + strP1Hex;
                part1Hex.setLength(0);
            }
        }

        System.out.println("Part 1: " + strP1Hex);
        mapDescriptors[0] = strP1Hex;

        String strP2Hex = "";
        StringBuilder part2Bin = new StringBuilder();
        StringBuilder part2Hex = new StringBuilder();
        for (int row = 1; row < MAZE_HEIGHT - 1; row++) {
            for (int col = 1; col < MAZE_WIDTH - 1; col++) {
                if (arenaMap.getCell(row, col).isExplored()) {
                    if (arenaMap.getCell(row, col).isBlocked())
                        part2Bin.append("1");
                    else
                        part2Bin.append("0");
                }
            }
        }

        for (int i = part2Bin.length() - 1; i >= 0 ;i--) {
            part2Hex.insert(0,part2Bin.charAt(i));
            if (part2Hex.length() == 4) {
                strP2Hex = binToHex(part2Hex.toString()) + strP2Hex;
                part2Hex.setLength(0);
            }
        }
        mapDescriptors[1] = strP2Hex;
        System.out.println("Part 2: " + strP2Hex);

        return mapDescriptors;
    }

    public static String binToHex(String value) {
        int bin = 0;

        try {

            bin = Integer.parseInt(value, 2);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Integer.toString(bin,16);
    }

    public static String toAndroid(MazeSolver currentMap) {
	    String mapData;

	    StringBuilder sb = new StringBuilder();

        for (int row = 1; row < MAZE_HEIGHT - 1; row++) {
            for (int col = 1; col < MAZE_WIDTH - 1; col++) {
	            if (!currentMap.getCell(row,col).isExplored())
	                sb.append("3");
	            else {
	                    if (currentMap.getCell(row,col).isVirtualWall()) {
	                        if (!currentMap.getCell(row,col).isBlocked())
	                            sb.append("1");
	                        else
	                            sb.append("2");
                        }
                        else {
                            if (currentMap.getCell(row,col).isBlocked())
                                sb.append("2");
                            else
                                sb.append("0");
                            }
	                }
	            }
            }

        mapData = sb.toString();
        return mapData;
    }

}
