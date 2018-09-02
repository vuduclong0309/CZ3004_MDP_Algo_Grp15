package simulator;

import java.awt.*;

public class GridCell {
	
	public static final int SIZE = 30;
	public static final int CELLBORDERWEIGHT = 2;
	public static final Color START = Color.GREEN;
	public static final Color GOAL = Color.YELLOW;
	public static final Color UNEXPLORED = Color.RED;
	public static final Color OBSTACLE = Color.BLACK;
	public static final Color EMPTY = Color.WHITE;
	
	
	private boolean obstacle;
	private boolean explored;
	private boolean virtualWall;
	private int row;
	private int col;
	
	public GridCell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isObstacle() {
		return obstacle;
	}

	public void setAsObstacle(boolean obstacle) {
		this.obstacle = obstacle;
	}

	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean explored) {
		this.explored = explored;
	}

	public boolean isVirtualWall() {
		return virtualWall;
	}

	public void setVirtualWall(boolean virtualWall) {
		this.virtualWall = virtualWall;
	}
	
	
	
	
}
