package object;

public class RobotDirection {

	  public static final int GOAL_ROW = 18;                          // row no. of goal cell
	    public static final int GOAL_COL = 13;                          // col no. of goal cell
	    public static final int START_ROW = 1;                          // row no. of start cell
	    public static final int START_COL = 1;                          // col no. of start cell
	    public static final int MOVE_COST = 10;                         // cost of FORWARD, BACKWARD movement
	    public static final int TURN_COST = 20;                         // cost of RIGHT, LEFT movement
	    public static final int SPEED = 100;                            // delay between movements (ms)
	    public static final DIRECTION START_DIR = DIRECTION.NORTH;      // start direction
	

	    public static final int INFINITE_COST = 9999;
	
	    
	    public enum DIRECTION {
		NORTH, SOUTH, EAST, WEST;

	        public static DIRECTION getNext(DIRECTION curDirection) {
	            return values()[(curDirection.ordinal() + 1) % values().length];
	        }

	        public static DIRECTION getPrevious(DIRECTION curDirection) {
	            return values()[(curDirection.ordinal() + values().length - 1) % values().length];
	        }

	        public static char print(DIRECTION d) {
	            switch (d) {
	                case NORTH:
	                    return 'N';
	                case EAST:
	                    return 'E';
	                case SOUTH:
	                    return 'S';
	                case WEST:
	                    return 'W';
	                default:
	                    return 'X';
	            }
	        }
	    }

	    public enum MOVEMENT {
	        FORWARD, BACKWARD, RIGHT, LEFT, CALIBRATE, ERROR;

	        public static char print(MOVEMENT m) {
	            switch (m) {
	                case FORWARD:
	                    return 'w';
	                case BACKWARD:
	                    return 's';
	                case RIGHT:
	                    return 'd';
	                case LEFT:
	                    return 'a';
	                case CALIBRATE:
	                    return 'c';
	                case ERROR:
	                default:
	                    return 'e';
	            }
	     }
	 }
}

