package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command that moves the robot from the right starting position to .5 meters away from the right lift
 * with the gear side of the robot squared up with the lift wall. Will time out after 5 seconds.
 */
public class MoveFromStartToLiftRight extends CommandGroup {
    public static final double DISTANCE_TO_TRAVEL = 1.75;
    public static final double SUPPLEMENTAL_DISTANCE = 0.15;
    public static final int HEADING_TO_FACE = -60;

    public MoveFromStartToLiftRight() {
        addSequential(new DriveStraightCommand(DISTANCE_TO_TRAVEL));
        addSequential(new TurnToHeading(HEADING_TO_FACE));
        //addSequential(new DriveHeadingAndDistance(HEADING_TO_FACE, SUPPLEMENTAL_DISTANCE));
        addSequential(new DriveForwardUntilVisionTargetCommand());
        addSequential(new DeliverGearCommand());
    }
}
