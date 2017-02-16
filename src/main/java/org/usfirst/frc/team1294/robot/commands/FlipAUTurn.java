package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Created by jxlewis on 2/11/17.
 */
public class FlipAUTurn extends PIDCommand {

  private static final double kP = 0.01;
  private static final double kI = 0.00;
  private static final double kD = 0.00;
  private static final double MAX_RATE = 0.25;

  private final double kToleranceDegrees = 5.f;

  private boolean hasRunReturnPidInputAtLeastOnce;

  public FlipAUTurn() {
    super("FlipAUTurn", kP, kI, kD);
    requires(Robot.driveSubsystem);

    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    getPIDController().setAbsoluteTolerance(kToleranceDegrees);
    getPIDController().setContinuous(true);
  }

  @Override
  protected void initialize() {
    hasRunReturnPidInputAtLeastOnce = false;
    setSetpoint((Robot.spatialAwarenessSubsystem.getHeading() + 180) % 360);
  }

  @Override
  protected boolean isFinished() {
    return this.getPIDController().onTarget()
            && hasRunReturnPidInputAtLeastOnce
            && Math.abs(Robot.spatialAwarenessSubsystem.getRate()) <= 0.3;
  }


  @Override
  protected double returnPIDInput() {
    if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
    return Robot.spatialAwarenessSubsystem.getHeading();
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.driveSubsystem.mecanumDrive(0, 0, output, Robot.spatialAwarenessSubsystem.getHeading());
  }
}
