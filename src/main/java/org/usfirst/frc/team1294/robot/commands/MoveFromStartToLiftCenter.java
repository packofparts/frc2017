package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command that moves the robot from the center starting position to .5 meters away from the center lift
 * with the gear side of the robot squared up with the lift wall. Will time out after 5 seconds.
 */
public class MoveFromStartToLiftCenter extends CommandGroup {
    public static final double DISTANCE_TO_TRAVEL = 1;
    public static final int HEADING_TO_FACE = 0;
    private static final double MORE_DISTANCE = 0.01;

    public MoveFromStartToLiftCenter() {
        addSequential(new DriveStraightCommand(DISTANCE_TO_TRAVEL));
        addSequential(new TurnToHeading(HEADING_TO_FACE));
        addSequential(new DeliverGearCommand());
        addSequential(new DriveStraightCommand(MORE_DISTANCE));
    }
}
