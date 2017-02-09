package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

public class SimpleGyroTeleopDriveCommand extends PIDCommand {
  private static final double P = 0.01;
  private static final double I = 0;
  private static final double D = 0;
  private static final double ABS_TOLERANCE = 5;

  private double z = 0;

  public SimpleGyroTeleopDriveCommand() {
    super("SimpleGyroTeleopDriveCommand", P, I, D);
    requires(Robot.driveSubsystem);
    getPIDController().setAbsoluteTolerance(ABS_TOLERANCE);
    getPIDController().setInputRange(0, 360);
    getPIDController().setOutputRange(-1, 1);
    getPIDController().setContinuous(true);
    SmartDashboard.putData("SimpleGyroTeleopDriveCommandPID", getPIDController());
  }

  @Override
  protected void initialize() {
    getPIDController().setSetpoint(Robot.driveSubsystem.getAngle());
  }

  @Override
  protected void execute() {
    XboxController joystick = Robot.oi.getJoystick();

    double leftTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kLeft);
    double rightTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kRight);
    double zRate = rightTriggerValue - leftTriggerValue;
    if (Math.abs(zRate) > .05 || Math.abs(Robot.driveSubsystem.getRate()) > 2) {
      if (getPIDController().isEnabled()) {
        getPIDController().disable();
      }
      z = zRate;
    } else {
      if (!getPIDController().isEnabled()) {
        getPIDController().setSetpoint(Robot.driveSubsystem.getAngle());
        getPIDController().enable();
      }
    }


    double x = joystick.getX(GenericHID.Hand.kRight);
    double y = joystick.getY(GenericHID.Hand.kRight);

    SmartDashboard.putNumber("driveX", x);
    SmartDashboard.putNumber("driveY", y);
    SmartDashboard.putNumber("driveZ", z);
    Robot.driveSubsystem.mecanumDrive(x, y, z, 0);
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getAngle();
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
