package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Austin Jenchi (timtim17)
 */
public class ResetGyroCommand extends Command {
  public ResetGyroCommand() {
    super("Reset gyro");
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {
    Robot.spatialAwarenessSubsystem.resetGyro();

  }

  @Override
  protected void execute() {

  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {

  }

  @Override
  protected void interrupted() {

  }
}
