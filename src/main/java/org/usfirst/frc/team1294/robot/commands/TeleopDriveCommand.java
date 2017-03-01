package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1294.robot.Robot;

public class TeleopDriveCommand extends PIDCommand {
  private static final double P = 0.01;
  private static final double I = 0;
  private static final double D = 0;
  private static final double ABS_TOLERANCE = 5;
  private static final double DEADZONE = 0.05;
  private static /*final*/ double TRIGGER_DEADZONE = 0.05;
  private static final double MAX_TURN_RATE = 0.2;

  private XboxController joystick;
  private double pidZ = 0;

  private enum DriveMode { OpenLoop, PID;}
  private DriveMode driveMode = DriveMode.OpenLoop;

  public TeleopDriveCommand() {
    super("TeleopDriveCommand", P, I, D);

    requires(Robot.driveSubsystem);

    joystick = Robot.oi.getJoystick();

    getPIDController().setAbsoluteTolerance(ABS_TOLERANCE);
    getPIDController().setOutputRange(-MAX_TURN_RATE, MAX_TURN_RATE);
    getPIDController().setContinuous(true);
//    SmartDashboard.putData("SimpleGyroTeleopDriveCommandPID", getPIDController());
    switchToPidSteering();
    switchToOpenLoopSteering();
  }

  @Override
  protected void initialize() {
//    getPIDController().setSetpoint(Robot.driveSubsystem.getHeading());
//    driveMode = DriveMode.OpenLoop;
//    getPIDController().disable();
    switchToPidSteering();
    switchToOpenLoopSteering();
    Robot.driveSubsystem.enableBrakeMode(true);
//    SmartDashboard.putNumber("trig", TRIGGER_DEADZONE);
  }

  @Override
  protected void end() {
    Robot.driveSubsystem.enableBrakeMode(false);
  }

  @Override
  protected void execute() {
//    TRIGGER_DEADZONE = SmartDashboard.getNumber("trig", TRIGGER_DEADZONE);

    double z; // this will contain the rotation rate (from either the joystick or the PID as appropriate)
    double joystickZ = getJoystickZ(); // temp variable to hold the joystick rotation rate

    if (shouldBeOpenLoopSteering(joystickZ)) {
      // robot should be in open loop steering mode where the joystick triggers control the rotation rate

      // if we are in PID steering mode, switch to open loop steering mode
      if (driveMode == DriveMode.PID) {
        switchToOpenLoopSteering();
      }

      // use the joystick to control the rotation rate
      z = joystickZ / 2;

      getPIDController().setSetpoint(Robot.spatialAwarenessSubsystem.getHeading());
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
              Robot.spatialAwarenessSubsystem.getHeading());
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
    return Math.abs(joystickZ) > TRIGGER_DEADZONE || Math.abs(Robot.spatialAwarenessSubsystem.getRate()) > 2 || allInputsInDeadZone();
  }

  private void switchToOpenLoopSteering() {
    driveMode = DriveMode.OpenLoop;
    getPIDController().disable();
  }

  private void switchToPidSteering() {
    driveMode = DriveMode.PID;
    getPIDController().enable();
    getPIDController().setSetpoint(Robot.spatialAwarenessSubsystem.getHeading());
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

  private boolean allInputsInDeadZone() {
    return Math.abs(getJoystickZ()) <  TRIGGER_DEADZONE &&
            Math.abs(joystick.getX(GenericHID.Hand.kLeft)) < DEADZONE &&
            Math.abs(joystick.getY(GenericHID.Hand.kLeft)) < DEADZONE &&
            Math.abs(joystick.getX(GenericHID.Hand.kRight)) < DEADZONE &&
            Math.abs(joystick.getY(GenericHID.Hand.kRight)) < DEADZONE;

  }

  @Override
  protected double returnPIDInput() {
    return Robot.spatialAwarenessSubsystem.getHeading();
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
