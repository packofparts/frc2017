package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class MoveFromStartToLiftRightWithShooter extends CommandGroup{
	public static final double DISTANCE_TO_TRAVEL = 1.8;
    public static final int HEADING_TO_FACE = -90;
    private static final double TIME_TO_WAIT = 3.0;
    private static final double DISTANCE_TO_REVERSE = -0.5;
    
    public MoveFromStartToLiftRightWithShooter() {
    	addSequential(new DriveStraightCommand(DISTANCE_TO_TRAVEL));
        addSequential(new TurnToHeading(HEADING_TO_FACE));
        addSequential(new DeliverGearCommand());
        addSequential(new WaitCommand(TIME_TO_WAIT));
        addSequential(new ShooterCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
    }
}
