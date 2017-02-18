package org.usfirst.frc.team1294.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.commands.TeleopDriveCommand;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveSubsystem extends Subsystem {

  private static final double RAMP_RATE = 48.0;

  private final CANTalon leftFrontTalon;
  private final CANTalon leftRearTalon;
  private final CANTalon rightFrontTalon;
  private final CANTalon rightRearTalon;
  public final CANTalon leftFrontTalon;
  public final CANTalon leftRearTalon;
  public final CANTalon rightFrontTalon;
  public final CANTalon rightRearTalon;
  public final double HEADING_TO_TRAVEL_C = 0;
  public final double DISTANCE_TO_TRAVEL_C = 1.99;
  public final double HEADING_TO_FACE_C = 0;
  public final double DISTANCE_TO_TRAVEL_R = 4.8;
  public final double HEADING_TO_TRAVEL_R = 30.0;
  public final double HEADING_TO_FACE_R = -90.0;
  public final double DISTANCE_TO_TRAVEL_L = 4.8;
  public final double HEADING_TO_TRAVEL_L = -30.0;
  public final double HEADING_TO_FACE_L = 90.0;
  private final RobotDrive robotDrive;

  public DriveSubsystem() {
    super("DriveSubsystem");

    leftFrontTalon = new CANTalon(RobotMap.TALON_DRIVEBASE_LEFT_FRONT);
    leftRearTalon = new CANTalon(RobotMap.TALON_DRIVEBASE_LEFT_REAR);
    rightFrontTalon = new CANTalon(RobotMap.TALON_DRIVEBASE_RIGHT_FRONT);
    rightRearTalon = new CANTalon(RobotMap.TALON_DRIVEBASE_RIGHT_REAR);

    leftFrontTalon.setVoltageRampRate(RAMP_RATE);
    rightFrontTalon.setVoltageRampRate(RAMP_RATE);
    leftRearTalon.setVoltageRampRate(RAMP_RATE);
    rightRearTalon.setVoltageRampRate(RAMP_RATE);

    leftFrontTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    rightRearTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    rightRearTalon.reverseSensor(true);

    robotDrive = new RobotDrive(leftFrontTalon, leftRearTalon, rightFrontTalon, rightRearTalon);
    robotDrive.setSafetyEnabled(false);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new TeleopDriveCommand());
  }

  public void mecanumDrive(double x, double y, double rotate, double gyro) {
    robotDrive.mecanumDrive_Cartesian(x, y, rotate, gyro);
  }

  public double getEncoderX() {
    return leftFrontTalon.getPosition() * 0.24;
  }

  public double getEncoderY() {
    return rightRearTalon.getPosition() * 0.25;
  }

  public void resetEncoders() {
    leftFrontTalon.setEncPosition(0);
    rightRearTalon.setEncPosition(0);
  }

  public void enableBrakeMode(boolean enabled) {
    this.leftFrontTalon.enableBrakeMode(enabled);
    this.rightFrontTalon.enableBrakeMode(enabled);
    this.leftRearTalon.enableBrakeMode(enabled);
    this.rightRearTalon.enableBrakeMode(enabled);
  }

  public void setLeftFrontTalon(double output) {
    this.leftFrontTalon.set(output);
  }

  public void setLeftRearTalon(double output) {
    this.leftRearTalon.set(output);
  }

  public void setRightFrontTalon(double output) {
    this.rightFrontTalon.set(output);
  }

  public void setRightRearTalon(double output) {
    this.rightRearTalon.set(output);
  }

  public void stop() {
    robotDrive.stopMotor();
  }
}
