package org.usfirst.frc.team1294.robot.subsystems;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team1294.robot.RobotMap;
import org.usfirst.frc.team1294.robot.commands.MecanumDriveCommand;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveSubsystem extends Subsystem {

  public final CANTalon leftFrontTalon;
  public final CANTalon leftRearTalon;
  public final CANTalon rightFrontTalon;
  public final CANTalon rightRearTalon;
  //Alliance Wall is 6.1722 meters(243 in)
  // robot placed middle of Alliance Wall
  // ~3.851 meters from right or left
  public final double HEADING_TO_TRAVEL_C = 0;
  public final double DISTANCE_TO_TRAVEL_C = 1.99;
  public final double HEADING_TO_FACE_C = 0;

  // robot placed ~2.57 meters from right
  // Distance is 5.4 meters all the way to the wall 
  public final double DISTANCE_TO_TRAVEL_R = 4.8;
  public final double HEADING_TO_TRAVEL_R = 30.0;
  public final double HEADING_TO_FACE_R = -90.0;
  
  // Robot placed ~2.57 from left
  //Distance is 5.4 meters all the way to the wall
  public final double DISTANCE_TO_TRAVEL_L = 4.8;
  public final double HEADING_TO_TRAVEL_L = -30.0;
  public final double HEADING_TO_FACE_L = 90.0;
  private final RobotDrive robotDrive;
  private static AHRS navX;

  public DriveSubsystem() {
    super("DriveSubsystem");

    leftFrontTalon = new CANTalon(RobotMap.DRIVEBASE_LEFT_FRONT_TALON);
    leftRearTalon = new CANTalon(RobotMap.DRIVEBASE_LEFT_REAR_TALON);
    rightFrontTalon = new CANTalon(RobotMap.DRIVEBASE_RIGHT_FRONT_TALON);
    rightRearTalon = new CANTalon(RobotMap.DRIVEBASE_RIGHT_REAR_TALON);
    robotDrive = new RobotDrive(leftFrontTalon, leftRearTalon, rightFrontTalon, rightRearTalon);
    robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
    navX = new AHRS(SerialPort.Port.kMXP);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new MecanumDriveCommand());
  }

  public void mecanumDrive(double x, double y, double rotate, double gyro) {
    robotDrive.mecanumDrive_Cartesian(x, y, rotate, gyro);
    //robotDrive.mecanumDrive_Cartesian();
  }

  public double getAngle() {
    double angle = navX.getAngle();
    System.out.println(angle);
    return angle;
  }


  public void resetGyro() {
    navX.reset();
  }
}
