package org.usfirst.frc.team1294.robot.subsystems;

import com.ctre.CANTalon;

import org.usfirst.frc.team1294.robot.commands.MecanumDriveCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveSubsystem extends Subsystem {

  public final CANTalon leftFrontTalon;
  public final CANTalon leftRearTalon;
  public final CANTalon rightFrontTalon;
  public final CANTalon rightRearTalon;
  public final RobotDrive robotDrive;

  public DriveSubsystem() {
    super("DriveSubsystem");

    leftFrontTalon = new CANTalon(2);
    leftRearTalon = new CANTalon(3);
    rightFrontTalon = new CANTalon(1);
    rightRearTalon = new CANTalon(4);
    robotDrive = new RobotDrive(leftFrontTalon, leftRearTalon, rightFrontTalon, rightRearTalon);
    robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
  }

  @Override
  protected void initDefaultCommand() {
    setDefaultCommand(new MecanumDriveCommand());
  }

  public void mecanumDrive(double x, double y, double rotate, double gyro) {
    robotDrive.mecanumDrive_Cartesian(x, y, rotate, gyro);
  }
}
