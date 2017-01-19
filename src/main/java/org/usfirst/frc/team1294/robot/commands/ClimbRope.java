package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbRope extends Command {

  public ClimbRope() {
    super("Drive motor");
    requires(Robot.climbingSubsystem);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    XboxController joystick = Robot.oi.getJoystick();
    if (joystick.getBumper()) {
      Robot.climbingSubsystem.climbTalon.set(0.5);
    } else {
	      Robot.climbingSubsystem.climbTalon.set(0);
	    }
    }

  @Override
  protected void end() {
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
