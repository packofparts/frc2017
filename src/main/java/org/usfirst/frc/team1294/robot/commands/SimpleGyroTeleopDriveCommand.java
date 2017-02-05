package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

public class SimpleGyroTeleopDriveCommand extends PIDCommand {
  private static final double P = 0.1;
  private static final double I = 0;
  private static final double D = 0;
  private static final double PERCENT_TOLERANCE = 0.1;

  private double z = 0;

  public SimpleGyroTeleopDriveCommand() {
    super("SimpleGyroTeleopDriveCommand", P, I, D);
    requires(Robot.driveSubsystem);
    getPIDController().setPercentTolerance(PERCENT_TOLERANCE);
    getPIDController().setInputRange(-5, 5);
    getPIDController().setOutputRange(-1, 1);
    SmartDashboard.putData("SimpleGyroTeleopDriveCommandPID", getPIDController());
  }

  @Override
  protected void execute() {
    XboxController joystick = Robot.oi.getJoystick();

    double leftTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kLeft);
    double rightTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kRight);
    getPIDController().setSetpoint(rightTriggerValue - leftTriggerValue);

    double x = joystick.getX(GenericHID.Hand.kRight);
    double y = joystick.getY(GenericHID.Hand.kRight);

    System.out.printf("SimpleGyroTeleopDriveCommand X:%.2f Y:%.2f Z:%.2f%n", x,y,z);
    Robot.driveSubsystem.mecanumDrive(x, y, z, 0);
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getRate();
  }

  @Override
  protected void usePIDOutput(double output) {
    z = output;
  }

  @Override
  protected boolean isFinished() {
    return false; // this is a default command, it never stops
  }
}
