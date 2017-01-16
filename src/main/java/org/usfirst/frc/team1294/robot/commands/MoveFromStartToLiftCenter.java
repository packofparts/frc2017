package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command that moves the robot from the center starting position to .5 meters away from the center lift
 * with the gear side of the robot squared up with the lift wall. Will time out after 5 seconds.
 */
public class MoveFromStartToLiftCenter extends CommandGroup {

    public static final int HEADING_TO_TRAVEL = 0;
    public static final double DISTANCE_TO_TRAVEL = 0.5;
    public static final int HEADING_TO_FACE = 90;

    public MoveFromStartToLiftCenter() {
        addParallel(new DriveHeadingAndDistance(HEADING_TO_TRAVEL, DISTANCE_TO_TRAVEL));
        addParallel(new TurnToHeading(HEADING_TO_FACE));
    }
}
