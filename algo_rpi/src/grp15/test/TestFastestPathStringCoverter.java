package grp15.test;

import java.util.ArrayList;

import grp15.algorithm.FastestPathAlgorithm;

import static grp15.object.Robot.*;

public class TestFastestPathStringCoverter {
    public static void main(String[] args) {
        ArrayList<Integer> movePath = new ArrayList<Integer>();
        movePath.add(MOVE_FORWARD);
        movePath.add(MOVE_FORWARD);
        movePath.add(TURN_LEFT);
        movePath.add(TURN_LEFT);
        movePath.add(TURN_RIGHT);
        movePath.add(MOVE_FORWARD);
        movePath.add(TURN_LEFT);
        movePath.add(MOVE_FORWARD);
        System.out.println(FastestPathAlgorithm.movePathToSignalString(movePath));
        System.out.println(FastestPathAlgorithm.pConvert(FastestPathAlgorithm.movePathToSignalString(movePath), 2));
    }
}
