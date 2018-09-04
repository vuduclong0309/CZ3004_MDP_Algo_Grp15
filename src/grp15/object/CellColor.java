package grp15.object;

import java.awt.Color;

public interface CellColor {
    //blocked area
    static final Color BLOCKED = Color.BLACK;

    //free area
    static final Color FREE = Color.WHITE;

    //start zone
    static final Color START = Color.GREEN;

    //end zone
    static final Color GOAL = Color.RED;

    //unexplored area
    static final Color UNEXPLORED = Color.DARK_GRAY;

    //robot head
    static final Color ROBOT_HEAD = Color.YELLOW;

    //robot body
    static final Color ROBOT_BODY = Color.GRAY;

    //explore path
    static final Color PATH = Color.MAGENTA;

    //shortest path
    static final Color SPATH = Color.CYAN;
}