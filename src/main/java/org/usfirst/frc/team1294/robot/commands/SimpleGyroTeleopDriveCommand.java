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
  private static final double DEADZONE = 0.05;

  private XboxController joystick;
  private double pidZ = 0;

  private enum DriveMode { OpenLoop, PID;}
  private DriveMode driveMode = DriveMode.OpenLoop;

  public SimpleGyroTeleopDriveCommand() {
    super("SimpleGyroTeleopDriveCommand", P, I, D);

    requires(Robot.driveSubsystem);

    joystick = Robot.oi.getJoystick();

    getPIDController().setAbsoluteTolerance(ABS_TOLERANCE);
    getPIDController().setInputRange(0, 360);
    getPIDController().setOutputRange(-1, 1);
    getPIDController().setContinuous(true);
    SmartDashboard.putData("SimpleGyroTeleopDriveCommandPID", getPIDController());
  }

  @Override
  protected void execute() {
    double z; // this will contain the rotation rate (from either the joystick or the PID as appropriate)
    double joystickZ = getJoystickZ(); // temp variable to hold the joystick rotation rate

    if (shouldBeOpenLoopSteering(joystickZ)) {
      // robot should be in open loop steering mode where the joystick triggers control the rotation rate

      // if we are in PID steering mode, switch to open loop steering mode
      if (driveMode == DriveMode.PID) {
        switchToOpenLoopSteering();
      }

      // use the joystick to control the rotation rate
      z = joystickZ;
    } else {
      // robot should be in pid steering mode where the PIDController controls the rotation rate

      // if we are in open loop steering, switch to PID steering
      if (driveMode == DriveMode.OpenLoop) {
        switchToPidSteering();
      }
      // otherwise use the PID to control the rotation rate
      z = pidZ;
    }

    if (shouldUseFieldOrientedDrive()) {
      // use the left analog stick for field oriented
      Robot.driveSubsystem.mecanumDrive(
              joystick.getX(GenericHID.Hand.kLeft),
              joystick.getY(GenericHID.Hand.kLeft),
              z,
              Robot.driveSubsystem.getAngle());
    } else {
      // otherwise use the right analog stick for robot oriented
      Robot.driveSubsystem.mecanumDrive(
              joystick.getX(GenericHID.Hand.kRight),
              joystick.getY(GenericHID.Hand.kRight),
              z,
              0);
    }
  }

  private double getJoystickZ() {
    double leftTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kLeft);
    double rightTriggerValue = joystick.getTriggerAxis(GenericHID.Hand.kRight);
    return rightTriggerValue - leftTriggerValue;
  }

  private boolean shouldBeOpenLoopSteering(double joystickZ) {
    if (driveMode == DriveMode.OpenLoop) {
      // stay in open loop if either the joystick is out of the deadzone or the robot is still rotating too fast
      return Math.abs(joystickZ) > DEADZONE || Math.abs(Robot.driveSubsystem.getRate()) > 2;
    } else {
      // only stay in closed loop if the joystick is in the deadzone
      return Math.abs(joystickZ) > DEADZONE;
    }
  }

  private void switchToOpenLoopSteering() {
    driveMode = DriveMode.OpenLoop;
  }

  private void switchToPidSteering() {
    getPIDController().setSetpoint(Robot.driveSubsystem.getAngle());
    driveMode = DriveMode.PID;
  }

  private boolean shouldUseFieldOrientedDrive() {
    double absXL = Math.abs(joystick.getX(GenericHID.Hand.kLeft));
    absXL = absXL < DEADZONE ? 0 : absXL;
    double absXR = Math.abs(joystick.getX(GenericHID.Hand.kRight));
    absXR = absXR < DEADZONE ? 0 : absXR;
    double abxYL = Math.abs(joystick.getY(GenericHID.Hand.kLeft));
    abxYL = abxYL < DEADZONE ? 0 : abxYL;
    double absYR = Math.abs(joystick.getY(GenericHID.Hand.kRight));
    absYR = absYR < DEADZONE ? 0 : absYR;
    return absXL > absXR || abxYL > absYR;
  }

  @Override
  protected double returnPIDInput() {
    return Robot.driveSubsystem.getAngle();
  }

  @Override
  protected void usePIDOutput(double output) {
    pidZ = output;
  }

  @Override
  protected boolean isFinished() {
    return false; // this is a default command, it never stops
  }
}
