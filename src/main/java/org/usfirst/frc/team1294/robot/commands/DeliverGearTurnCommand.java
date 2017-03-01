package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.vision.VisionProcessing;

/**
 * Not intended for standalone use. Must be used as part of the DeliverGearCommand CommandGroup.
 * Sets the z rate in the parent CommandGroup to keep the robot pointing at the vision target.
 */
public class DeliverGearTurnCommand extends PIDCommand {

  private static final double TOLERANCE = 5.0f;
  private static final double KP = 0.01f;
  private static final double KI = 0;
  private static final double KD = 0;
  private static final double MAX_RATE = 0.08;

  private final DeliverGearCommand parent;
  private final Timer timer;

  public DeliverGearTurnCommand(DeliverGearCommand parent) {
    super("DeliverGearTurnCommand", KP, KI, KD);

    this.parent = parent;

    requires(Robot.spatialAwarenessSubsystem);

    getPIDController().setAbsoluteTolerance(TOLERANCE);
    getPIDController().setOutputRange(-MAX_RATE, MAX_RATE);
    getPIDController().setSetpoint(0);
//    SmartDashboard.putData("DeliverGearTurnCommandPID", getPIDController());

    timer = new Timer();
  }

  @Override
  protected void initialize() {
    timer.start();
  }

  @Override
  protected void execute() {
    // do vision processing
    VisionProcessing.VisionProcessingResult visionProcessingResult = Robot.spatialAwarenessSubsystem.doVisionProcessingOnGearCamera();

    // if the target was acquired, adjust the setpoint
    if (visionProcessingResult.targetAcquired) {
      getPIDController().setSetpoint(visionProcessingResult.headingWhenImageTaken + visionProcessingResult.degreesOffCenter);
    }

    // periodically save an image
    if (timer.hasPeriodPassed(.25)) {
      Robot.spatialAwarenessSubsystem.saveLastImage();
      timer.reset();
    }

    parent.setVisionTargetAcquired(visionProcessingResult.targetAcquired);
  }

  @Override
  protected double returnPIDInput() {
    return Robot.spatialAwarenessSubsystem.getHeading();
  }

  @Override
  protected void usePIDOutput(double output) {
    parent.setzRate(output);
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
