package simulator;

import java.awt.*;
import javax.swing.JPanel;

import robot.Robot;

public class Map extends JPanel {
	
	/*
	 * Constants for the map
	 */
	public static final int MAPROWS = 20;
	public static final int MAPCOLS = 15;
	public static final int MAPSIZE = 300;
	public static final int GOAL_ROW_NO = 18;
	public static final int GOAL_COL_NO = 13;
	public static final int X_OFFSET = 80;
	public static final int MAPHEIGHT = 580;
	
	private Robot robot;
	private GridCell[][] cells;
	
	public Map(Robot robot) {
		
		this.robot = robot;
		
		cells = new GridCell[MAPROWS][MAPCOLS];
		for (int r = 0; r < MAPROWS; r++) {
			for (int c=0; c < MAPCOLS; c++) {
				cells[r][c] = new GridCell(r,c);
				if (r == 0 || c == 0 || r == MAPROWS - 1 || c == MAPCOLS-1) //Set virtual wall within arena to ensure is > 10cm from the real wall
					cells[r][c].setVirtualWall(true); 
			}
		}
		
	}

	/*
	 * 
	 */
	public void setAllUnexplored() {
		for (int r=0;r<MAPROWS;r++) {
			for (int c=0;c<MAPCOLS;c++) {
				if (r <=(GOAL_ROW_NO + 1) && r >= (GOAL_ROW_NO - 1) && c <= (GOAL_COL_NO + 1) && c >= (GOAL_COL_NO - 1)) {
					cells[r][c].setExplored(true);
				}
				else
					cells[r][c].setExplored(false);
				
			}
		}
	}
	
	 /*
     * Sets a cell as an obstacle and the surrounding cells as virtual walls or resets the cell and surrounding
     * virtual walls.
     */
    public void setObstacleNVirtual(int row, int col, boolean obstacle) {
    	// if it is in goal zone or start zone terminate this method
    	if ((obstacle && row >= 0 && row <= 2 && col >= 0 && col <= 2) || (obstacle && row <= GOAL_ROW_NO + 1 && row >= GOAL_ROW_NO - 1 && col <= GOAL_COL_NO + 1 && col >= GOAL_COL_NO - 1))
            return;

        cells[row][col].setAsObstacle(obstacle);

        if (row >= 1) {
        	cells[row - 1][col].setVirtualWall(obstacle); 

            if (col < MAPCOLS - 1) {
            	cells[row - 1][col + 1].setVirtualWall(obstacle);    
            }

            if (col >= 1) {
            	cells[row - 1][col - 1].setVirtualWall(obstacle);
            }
        }

        if (row < MAPROWS - 1) {
        	cells[row + 1][col].setVirtualWall(obstacle);

            if (col < MAPCOLS - 1) {
            	cells[row + 1][col + 1].setVirtualWall(obstacle);
            }

            if (col >= 1) {
            	cells[row + 1][col - 1].setVirtualWall(obstacle);
            }
        }

        if (col >= 1) {
        	cells[row][col - 1].setVirtualWall(obstacle);
        }

        if (col < MAPCOLS - 1) {
        	cells[row][col + 1].setVirtualWall(obstacle);
        }
    }
	

    /*
     * Returns a particular cell in the grid.
     */
    public GridCell getCell(int row, int col) {
        return cells[row][col];
    }

    /*
     * Returns true if a cell is an obstacle.
     */
    public boolean isObstacleCell(int row, int col) {
        return cells[row][col].isObstacle();
    }

    /*
     * Returns true if a cell is a virtual wall.
     */
    public boolean isVirtualWallCell(int row, int col) {
        return cells[row][col].isVirtualWall();
    }
    
    /*
     * It paints square cells for the grid with the appropriate colors as 
     * well as the robot on-screen.
     */
    //to continue
    
    
    public void paintComponent(Graphics g) {
    	 // Paint the cells with the appropriate colors.
    	Color cellColor;
    	for (int r = 0; r < MAPROWS; r++) {
            for (int c = 0; c < MAPCOLS; c++) {
                

                if (r >= 0 && r <= 2 && c >= 0 && c <= 2)
                    cellColor = GridCell.START;
                else if (r <= GOAL_ROW_NO + 1 && r >= GOAL_ROW_NO - 1 && c <= GOAL_COL_NO + 1 && c >= GOAL_COL_NO - 1)
                    cellColor = GridCell.GOAL;
                else {
                    if (!cells[r][c].isExplored())
                        cellColor = GridCell.UNEXPLORED;
                    else if (cells[r][c].isObstacle())
                        cellColor = GridCell.OBSTACLE;
                    else
                        cellColor = GridCell.EMPTY;
                }

                g.setColor(cellColor);
                g.fillRect(c * GridCell.SIZE + GridCell.CELLBORDERWEIGHT + X_OFFSET, MAPHEIGHT - (r*GridCell.SIZE-GridCell.CELLBORDERWEIGHT), GridCell.SIZE - (GridCell.CELLBORDERWEIGHT * 2),GridCell.SIZE - (GridCell.CELLBORDERWEIGHT * 2));

            }
        }
    }

	
}
