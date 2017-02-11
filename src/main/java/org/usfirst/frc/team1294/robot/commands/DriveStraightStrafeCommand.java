package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveStraightStrafeCommand extends PIDCommand {
  private static final double p = 0.1;
  private static final double i = 0.;
  private static final double d = 0.;
  private static final double TOLERANCE = .05;
  private boolean hasRunReturnPidInputAtLeastOnce;
  private static final double MAX_RATE = 0.2;

  public DriveStraightStrafeCommand() {
    super("DriveStraightStrafeCommand", p, i, d);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(0, 360);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    SmartDashboard.putData("DriveStraightStrafeCommandPID", getPIDController());
  }

  @Override
  protected void initialize() {
    Robot.driveSubsystem.resetEncoders();
    getPIDController().setSetpoint(0);
  }

  @Override
  protected double returnPIDInput() {
    if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
    return Robot.driveSubsystem.getEncoderX();
  }

  @Override
  protected void usePIDOutput(double output) {
    if (getGroup() instanceof DriveStraightCommand) {
      ((DriveStraightCommand) getGroup()).setxRate(output);
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
