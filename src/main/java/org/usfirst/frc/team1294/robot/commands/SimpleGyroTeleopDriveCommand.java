package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

import static org.usfirst.frc.team1294.robot.commands.MecanumDriveCommand.DEADZONE;

public class SimpleGyroTeleopDriveCommand extends PIDCommand {
  private static final double P = 0.01;
  private static final double I = 0;
  private static final double D = 0;
  private static final double ABS_TOLERANCE = 5;

  private double z = 0;

  private final double DEADZONE = 0.05;

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
    SmartDashboard.putNumber("driveZ", z);

    double absXL = Math.abs(joystick.getX(GenericHID.Hand.kLeft));
    absXL = absXL < DEADZONE ? 0 : absXL;
    double absXR = Math.abs(joystick.getX(GenericHID.Hand.kRight));
    absXR = absXR < DEADZONE ? 0 : absXR;
    double abxYL = Math.abs(joystick.getY(GenericHID.Hand.kLeft));
    abxYL = abxYL < DEADZONE ? 0 : abxYL;
    double absYR = Math.abs(joystick.getY(GenericHID.Hand.kRight));
    absYR = absYR < DEADZONE ? 0 : absYR;
    if (absXL > absXR
            || abxYL > absYR) {
      Robot.driveSubsystem.mecanumDrive(joystick.getX(GenericHID.Hand.kLeft),
              joystick.getY(GenericHID.Hand.kLeft),
              joystick.getTriggerAxis(GenericHID.Hand.kRight) - joystick.getTriggerAxis(GenericHID.Hand.kLeft),
              Robot.driveSubsystem.getAngle());
//      System.out.println("FIELD ORIENTED");
    } else {
      // otherwise use the right analog stick for robot oriented
      Robot.driveSubsystem.mecanumDrive(joystick.getX(GenericHID.Hand.kRight),
              joystick.getY(GenericHID.Hand.kRight),
              joystick.getTriggerAxis(GenericHID.Hand.kRight) - joystick.getTriggerAxis(GenericHID.Hand.kLeft),
              0);
//      System.out.println("ROBOT ORIENTED");
    }
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
