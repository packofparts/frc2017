package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

/**
 * a command that will drive the robot on the specified heading until the
 * specified distance had been traveled. Uses the encoder output from the
 * distance measuring wheels not the drive wheels.
 */
public class DriveHeadingAndDistance extends PIDCommand {

  private static final double P = 0.1;
  private static final double I = 0f;
  private static final double D = 0f;
  private static final double ABS_TOLERANCE = 0.1;
  private final double heading;
  private final double distanceInMeters;
  private final double MAX_SPEED = 0.5;
  private double rateX;
  private double rateY;
  private double scaleX;
  private double scaleY;
  private boolean hasRunReturnPidInputAtLeastOnce;

  public DriveHeadingAndDistance(double heading, double distanceInMeters) {
    super(P, I, D);

    getPIDController().setAbsoluteTolerance(ABS_TOLERANCE);
    getPIDController().setOutputRange(-MAX_SPEED, MAX_SPEED);
    requires(Robot.driveSubsystem);

    this.heading = heading;
    this.distanceInMeters = distanceInMeters;
    scaleX = distanceInMeters * Math.sin(heading);
    scaleY = distanceInMeters * Math.cos(heading);

    SmartDashboard.putData("DriveHeadingAndDistancePID", getPIDController());
  }

  @Override
  protected void initialize() {
    setSetpoint(distanceInMeters);
    Robot.driveSubsystem.resetEncoders();
  }

  @Override
  protected void execute() {
    Robot.driveSubsystem.mecanumDrive(rateX, -rateY, heading, Robot.spatialAwarenessSubsystem.getHeading());
  }

  @Override
  protected boolean isFinished() {
    return this.getPIDController().onTarget()
            && hasRunReturnPidInputAtLeastOnce;
  }

  @Override
  protected double returnPIDInput() {
    if (!hasRunReturnPidInputAtLeastOnce) hasRunReturnPidInputAtLeastOnce = true;
    return Math.sqrt(Math.pow(Robot.driveSubsystem.getEncoderX(), 2) + Math.pow(Robot.driveSubsystem.getEncoderY(), 2));
  }


  @Override
  protected void usePIDOutput(double output) {
    rateY = scaleY * output;
    rateX = scaleX * output;
  }
}
