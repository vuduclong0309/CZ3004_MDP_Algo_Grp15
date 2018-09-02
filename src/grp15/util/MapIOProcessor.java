package util;
import simulator.Map;


import java.io.*;

public class MapIOProcessor {
	
	/* 
	 * Reads input text file from storage that uses binary to indicate if a cell on the arena is an obstacle.
	 * */
	public static void readFile(String filename, Map map) {
		try {
			InputStream in = new FileInputStream("arenas/" + filename + ".txt");
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			
			String data = bf.readLine();
			StringBuilder sb = new StringBuilder();
			while (bf.readLine() != null) {
				sb.append(bf.readLine());
				data = bf.readLine();
			}
			
			String binData = sb.toString();
			int binDataPtr = 0;
			for (int r = 0 ; r < Map.MAPROWS ;r++) {
				for (int c = 0; c < Map.MAPCOLS; c++) {
					//if(binData.charAt(binDataPtr) == '1') map.setObstacleNVirtual
					
				}
			}
			bf.close();
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Convert binary to hexadecimal representation to be displayed
	 */
	public String binToHex(String value) {
		int deci = 0;
		
		try {
	
			deci = Integer.parseInt(value, 2);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Integer.toHexString(deci);
	}
	
	/*
	public static String[] mapDescriptor() {
		String[] mapDesc = new String[2];
		
		return map
	}
	*/
}
