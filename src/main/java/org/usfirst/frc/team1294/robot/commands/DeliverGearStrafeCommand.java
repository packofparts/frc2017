package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the x rate in the parent CommandGroup.
 */
public class DeliverGearStrafeCommand extends PIDCommand {
  private static final double STRAFE_TOLERANCE = 0.1f;
  private static final double STRAFE_KP = 0.1f;
  private static final double STRAFE_KI = 0;
  private static final double STRAFE_KD = 0;
  private static final double MAXIMUM_OUTPUT = 0.1;
  private static final double MAX_ALLOWED_DISTANCE_FROM_INITIAL = 0.1;

  private final DeliverGearCommand parent;
  private double initialEncoderX;

  public DeliverGearStrafeCommand(DeliverGearCommand parent) {
    super("DeliverGearStrafeCommand", STRAFE_KP, STRAFE_KI, STRAFE_KD);
    this.parent = parent;
    getPIDController().setAbsoluteTolerance(STRAFE_TOLERANCE);
    getPIDController().setOutputRange(-MAXIMUM_OUTPUT, MAXIMUM_OUTPUT);

    SmartDashboard.putData("DeliverGearStrafeCommandPID", getPIDController());
  }

  @Override
  protected void initialize() {
    initialEncoderX = Robot.driveSubsystem.getEncoderX();
    getPIDController().setSetpoint(initialEncoderX);
  }

  @Override
  protected void execute() {
    // only set a new strafing setpoint if vision target is acquired
    if (parent.isVisionTargetAcquired()) {
      double newSetpoint = Robot.driveSubsystem.getEncoderX() + Robot.spatialAwarenessSubsystem.getRightUltrasonicDistance() - Robot.spatialAwarenessSubsystem.getLeftUltrasonicDistance();

      // only set a new setpoint if it is within the max distance from initial
      if (Math.abs(initialEncoderX - newSetpoint) < MAX_ALLOWED_DISTANCE_FROM_INITIAL) {
        setSetpoint(newSetpoint);
      }
    }
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getEncoderX();
  }

  @Override
  protected void usePIDOutput(double output) {
    parent.setxRate(output);
  }

  @Override
  protected boolean isFinished() {
    // never finishes by itself, depends on the parent CommandGroup to do that
    return false;
  }

  public boolean onTarget() {
    return getPIDController().onTarget();
  }
}
