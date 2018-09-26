package util;

import grp15.simulator.MazeEditor;

import static grp15.simulator.MazeEditor.MAZE_HEIGHT;
import static grp15.simulator.MazeEditor.MAZE_WIDTH;

public class MapDescriptor {

    public static String[] generateMapDescriptor(MazeEditor arenaMap) {
        String[] mapDescriptors = new String[2];

        StringBuilder part1Hex = new StringBuilder();
        StringBuilder part1Bin = new StringBuilder();
        part1Bin.append("11");
        for (int row = 1; row < MAZE_HEIGHT; row++) {
            for (int col = 1; col < MAZE_WIDTH; col++) {
                if (arenaMap.getMazeMap()[row][col].isExplored())
                    part1Bin.append("1");
                else
                    part1Bin.append("0");

                if (part1Bin.length() == 4) {
                    part1Hex.append(binToHex(part1Bin.toString()));
                    part1Bin.setLength(0);
                }
            }
        }
        part1Bin.append("11");
        part1Hex.append(binToHex(part1Bin.toString()));
        System.out.println("Part 1: " + part1Hex.toString());
        mapDescriptors[0] = part1Hex.toString();

        StringBuilder part2Hex = new StringBuilder();
        StringBuilder part2Bin = new StringBuilder();
        for (int row = 0; row < MAZE_HEIGHT; row++) {
            for (int col = 0; col < MAZE_WIDTH; col++) {
                if (arenaMap.getMazeMap()[row][col].isExplored()) {
                    if (arenaMap.getMazeMap()[row][col].isBlocked())
                        part2Bin.append("1");
                    else
                        part2Bin.append("0");

                    if (part2Bin.length() == 4) {
                        part2Hex.append(binToHex(part2Bin.toString()));
                        part2Bin.setLength(0);
                    }
                }
            }
        }
        if (part2Bin.length() > 0)
            part2Hex.append(binToHex(part2Bin.toString()));
        System.out.println("Part 2: " + part2Hex.toString());
        mapDescriptors[1] = part2Hex.toString();

        return mapDescriptors;
    }

    public static String binToHex(String value) {
        int deci = 0;

        try {

            deci = Integer.parseInt(value, 16);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return String.valueOf(deci);
    }

}