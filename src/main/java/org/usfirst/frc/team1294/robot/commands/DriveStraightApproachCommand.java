package org.usfirst.frc.team1294.robot.commands;

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

  public DriveStraightApproachCommand() {
    super("DriveStraightTurnCommand", p, i, d);
    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(0, 360);
    getPIDController().setOutputRange(-1, 1);
    getPIDController().setSetpoint(Robot.driveSubsystem.getEncoderX());
    SmartDashboard.putData("DriveStraightApproachCommandPID", getPIDController());
  }

  @Override
  protected double returnPIDInput() {
    return 0;
  }

  @Override
  protected void usePIDOutput(double output) {

  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
