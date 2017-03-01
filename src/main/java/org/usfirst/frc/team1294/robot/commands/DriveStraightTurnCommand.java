package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveStraightTurnCommand extends PIDCommand {
  private static final double p = 0.01;
  private static final double i = 0.;
  private static final double d = 0.;
  private static final double TOLERANCE = 3;
  private boolean hasRunReturnPidInputAtLeastOnce;
  private static final double MAX_RATE = 0.25;

  public DriveStraightTurnCommand() {
    super("DriveStraightTurnCommand", p, i, d);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(0, 360);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
//    SmartDashboard.putData("DriveStraightTurnCommandPID", getPIDController());
  }

  @Override
  protected void initialize() {
    getPIDController().setSetpoint(Robot.spatialAwarenessSubsystem.getHeading());
  }

  @Override
  protected double returnPIDInput() {
    if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
    return Robot.spatialAwarenessSubsystem.getHeading();
  }

  // assuming angle is the error and target angle is 0

  protected void usePIDOutput(double output) {
	  if (getGroup() instanceof DriveStraightCommand) {
	      ((DriveStraightCommand) getGroup()).setzRate(output);
	    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return hasRunReturnPidInputAtLeastOnce && getPIDController().onTarget();
  }
}
