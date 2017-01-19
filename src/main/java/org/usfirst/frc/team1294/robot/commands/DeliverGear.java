package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1294.robot.Robot;
import org.usfirst.frc.team1294.robot.util.SimplePIDSource;

/**
 * A command that moves the robot from a position near the lift to where the pilot can
 * use the lift to retrieve the gear. Uses ultrasonic distance transducers to keep the
 * lift squared up with the gear side of the robot. Uses vision system to move the robot
 * side to side to keep the lift centered. Will time out after 10 seconds.
 */
public class DeliverGear extends Command {
  private static final double ANGLE_TOLERANCE_DEGREES = 2.0f;
  private static final double ANGLE_KP = 0.3f;
  private static final double ANGLE_KI = 0;
  private static final double ANGLE_KD = 0;

  private static final double STRAFE_TOLERANCE = 0.5f;
  private static final double STRAFE_KP = 0.3f;
  private static final double STRAFE_KI = 0;
  private static final double STRAFE_KD = 0;

  private static final double APPROACH_TOLERANCE = 0.5f;
  private static final double APPROACH_KP = 0.3f;
  private static final double APPROACH_KI = 0;
  private static final double APPROACH_KD = 0;

  private final PIDController anglePid;
  private final PIDController strafePid;
  private final PIDController approachPid;

  private double commandedTurnRate = 0;
  private double commandedStrafeRate = 0;
  private double commandedApproachRate = 0;

  public DeliverGear() {
    requires(Robot.driveSubsystem);
    requires(Robot.cameraSubsystem);

    anglePid = new PIDController(ANGLE_KP, ANGLE_KI, ANGLE_KD
            , new SimplePIDSource(Robot.driveSubsystem::getAngleToWall)
            , output -> commandedTurnRate = output);
    anglePid.setAbsoluteTolerance(ANGLE_TOLERANCE_DEGREES);
    anglePid.setSetpoint(0);

    strafePid = new PIDController(STRAFE_KP, STRAFE_KI, STRAFE_KD
            , new SimplePIDSource(this::offsetToLift)
            , output -> commandedStrafeRate = output);
    strafePid.setAbsoluteTolerance(STRAFE_TOLERANCE);
    strafePid.setSetpoint(0);

    approachPid = new PIDController(APPROACH_KP, APPROACH_KI, APPROACH_KD
            , new SimplePIDSource(Robot.driveSubsystem::getDistanceToWall)
            , output -> commandedApproachRate = output);
    approachPid.setAbsoluteTolerance(APPROACH_TOLERANCE);
    strafePid.setSetpoint(0.25); // todo: calibrate this distance
  }

  @Override
  protected void execute() {
    Robot.driveSubsystem.mecanumDrive(commandedStrafeRate, commandedApproachRate, commandedTurnRate, 0);
  }

  @Override
  protected boolean isFinished() {
    return anglePid.onTarget() && strafePid.onTarget() && approachPid.onTarget();
  }

  private double offsetToLift() {
    Robot.cameraSubsystem.doVisionProcessingOnGearCamera();
    if (Robot.cameraSubsystem.isGearTargetAcquired()) {
      return Robot.cameraSubsystem.getGearTargetPixelsFromCenter();
    } else {
      return 0;
    }
  }

}
