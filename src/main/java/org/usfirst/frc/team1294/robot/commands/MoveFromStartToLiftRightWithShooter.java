//**
//Authored by Alyssa Giedd
//**
package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class MoveFromStartToLiftRightWithShooter extends CommandGroup{
    private static final double TIME_TO_WAIT = 3.0;
    private static final double HEADING_TO_TURN = 45;
    //do the math for this distance later
    private static final double DISTANCE_TO_DRIVE = -1.5;
    public MoveFromStartToLiftRightWithShooter() {
    	addSequential(new MoveFromStartToLiftRight());
        addSequential(new WaitCommand(TIME_TO_WAIT));
        addSequential(new DriveStraightCommand(DISTANCE_TO_DRIVE));
        addSequential(new TurnToHeading(HEADING_TO_TURN));
        addSequential(new ShooterCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
        addSequential(new FeederCommand());
    }
}
