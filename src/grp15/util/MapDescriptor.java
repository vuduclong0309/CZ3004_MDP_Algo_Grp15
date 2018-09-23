package grp15.util;

//import object.Map;
import grp15.util.MapIOProcessor;

public class MapDescriptor {
	
	/*public static String[] generateMapDescriptor(Map map) {
        String[] mapDescriptors = new String[2];

        StringBuilder part1Hex = new StringBuilder();
        StringBuilder part1Bin = new StringBuilder();
        part1Bin.append("11");
        for (int row = 0; row < Map.MAPROWS; row++) {
            for (int col = 0; col < Map.MAPCOLS; col++) {
                if (map.getCell(row, col).isExplored())
                    part1Bin.append("1");
                else
                    part1Bin.append("0");

                if (part1Bin.length() == 4) {
                    part1Hex.append(MapIOProcessor.binToHex(part1Bin.toString()));
                    part1Bin.setLength(0);
                }
            }
        }
        part1Bin.append("11");
        part1Hex.append(MapIOProcessor.binToHex(part1Bin.toString()));
        System.out.println("Part 1: " + part1Hex.toString());
        mapDescriptors[0] = part1Hex.toString();

        StringBuilder part2Hex = new StringBuilder();
        StringBuilder part2Bin = new StringBuilder();
        for (int rol = 0; rol < Map.MAPROWS; rol++) {
            for (int col = 0; col < Map.MAPCOLS; col++) {
                if (map.getCell(rol, col).isExplored()) {
                    if (map.getCell(rol, col).isObstacle())
                        part2Bin.append("1");
                    else
                        part2Bin.append("0");

                    if (part2Bin.length() == 4) {
                        part2Hex.append(MapIOProcessor.binToHex(part2Bin.toString()));
                        part2Bin.setLength(0);
                    }
                }
            }
        }
        if (part2Bin.length() > 0) 
        	part2Hex.append(MapIOProcessor.binToHex(part2Bin.toString()));
        System.out.println("P2: " + part2Hex.toString());
        mapDescriptors[1] = part2Hex.toString();

        return mapDescriptors;
    }
*/
}
