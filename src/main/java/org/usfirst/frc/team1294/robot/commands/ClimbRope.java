package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ClimbRope extends Command {
	  private static double TRIGGER_DEADZONE = 0.05;
  public ClimbRope() {
    super("Drive motor");
    requires(Robot.climbingSubsystem);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
	if(Robot.oi.getJoystick2().getTriggerAxis(Hand.kRight) > TRIGGER_DEADZONE){
		Robot.driveSubsystem.enableBrakeMode(false);
	    Robot.climbingSubsystem.climbTalon.set(Robot.oi.getJoystick2().getTriggerAxis(Hand.kRight));
	}
	else if(Robot.oi.getJoystick2().getYButton() == true){
		Robot.climbingSubsystem.climbTalon.set(1.0);
	}
  }
  @Override
  protected void end() {
	Robot.driveSubsystem.enableBrakeMode(true);
    Robot.climbingSubsystem.climbTalon.set(0);
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
