package grp15.util;

import java.io.*;

import grp15.simulator.MazeEditor;


public class MapIOProcessor {
	/*
	 * Reads input text file from storage that uses binary to indicate if a cell on the arena is an obstacle.
	 * */
	public static void readMapFileFromDisk(File file, MazeEditor mz) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));

			char[][] mapEncoding = new char[20][15];
			int value = 0;
			int rowPtr = 0;
			int colPtr = 0;
			while ((value = bf.read()) != -1) {
				char binData = (char)value;
				mapEncoding[rowPtr][colPtr] = binData;

				colPtr++;
				if (colPtr == 15) {
					colPtr = 0;
					rowPtr++;
				}
				if (rowPtr == 20)
					break;
			}

			bf.close();
			mz.updateLoadedMap(mapEncoding);

			//map.setAllExplored();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
