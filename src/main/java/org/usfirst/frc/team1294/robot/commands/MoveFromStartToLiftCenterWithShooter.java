package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveFromStartToLiftCenterWithShooter extends CommandGroup{
	public static final double DISTANCE_TO_TRAVEL = 1.8;
    public static final int HEADING_TO_FACE = -90;
    private static final double TIME_TO_WAIT = 3.0;
    private static final double DISTANCE_TO_REVERSE = -0.5;
    
    public MoveFromStartToLiftCenterWithShooter() {
        addSequential(new DriveStraightCommand(DISTANCE_TO_TRAVEL));
        addSequential(new DeliverGearCommand());
        addSequential(new Wait(TIME_TO_WAIT));
        addSequential(new DriveStraightCommand(DISTANCE_TO_REVERSE));
        addSequential(new ShooterCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
    }
}
