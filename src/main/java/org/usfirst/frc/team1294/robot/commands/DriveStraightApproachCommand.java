package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveStraightApproachCommand extends PIDCommand {
  private static final double p = 0.;
  private static final double i = 0.;
  private static final double d = 0.;
  private static final double TOLERANCE = 2.;
  private static final double MAX_SPEED = 0.5;
  private final double distance;
  private boolean hasRunReturnPidInputAtLeastOnce;

  public DriveStraightApproachCommand(double distance) {
    super("DriveStraightTurnCommand", p, i, d);
    this.distance = distance;
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setOutputRange(-MAX_SPEED, MAX_SPEED);
    getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderY() + distance);
    SmartDashboard.putData("DriveStraightApproachCommandPID", getPIDController());
  }

  @Override
  protected void initialize() {
    Robot.driveSubsystem.resetEncoders();
    getPIDController().setSetpoint(this.distance);
  }

  @Override
  protected double returnPIDInput() {
    hasRunReturnPidInputAtLeastOnce = true;
    return Robot.driveSubsystem.getEncoderY();
  }

  @Override
  protected void usePIDOutput(double output) {
    CommandGroup group = getGroup();
    if (group instanceof DriveStraightCommand) {
      ((DriveStraightCommand) group).setyRate(output);
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
