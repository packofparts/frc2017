package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.vision.VisionProcessing;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the z rate in the parent CommandGroup to keep the robot pointing at the vision target.
 */
public class DeliverGearTurnCommand extends PIDCommand {

  private static final double TOLERANCE = 2.0f;
  private static final double KP = 0.3f;
  private static final double KI = 0;
  private static final double KD = 0;
  private static final double MAX_RATE = 0.25;

  public DeliverGearTurnCommand() {
    super("DeliverGearTurnCommand", KP, KI, KD);

    requires(Robot.spatialAwarenessSubsystem);

    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setInputRange(-180, 180);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    getPIDController().setSetpoint(0);
    SmartDashboard.putData("DeliverGearTurnCommandPID", getPIDController());
  }

  @Override
  protected void execute() {
    // record the angle before vision processing
    double angle = Robot.spatialAwarenessSubsystem.getHeading();

    // do vision processing
    VisionProcessing.VisionProcessingResult visionProcessingResult = Robot.spatialAwarenessSubsystem.doVisionProcessingOnGearCamera();

    // if the target was acquired, adjust the setpoint
    if (visionProcessingResult.targetAcquired) {
      getPIDController().setSetpoint(angle + visionProcessingResult.degreesOffCenter);
    }
  }

  @Override
  protected double returnPIDInput() {
    return Robot.spatialAwarenessSubsystem.getHeading();
  }

  @Override
  protected void usePIDOutput(double output) {
    if (getGroup() instanceof DeliverGearCommand) {
      ((DeliverGearCommand) getGroup()).setzRate(output);
    }
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
