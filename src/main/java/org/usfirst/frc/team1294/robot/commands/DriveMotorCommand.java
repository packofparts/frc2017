package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveMotorCommand extends Command {

  public DriveMotorCommand() {
    super("Drive motor");
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    XboxController joystick = Robot.oi.getJoystick();
    if (joystick.getAButton()) {
      Robot.driveSubsystem.leftFrontTalon.set(0.5);
    } else {
      Robot.driveSubsystem.leftFrontTalon.set(0);
    }

    if (joystick.getBButton()) {
      Robot.driveSubsystem.leftRearTalon.set(0.5);
    } else {
      Robot.driveSubsystem.leftRearTalon.set(0);
    }

    if (joystick.getXButton()) {
      Robot.driveSubsystem.rightFrontTalon.set(0.5);
    } else {
      Robot.driveSubsystem.rightFrontTalon.set(0);
    }

    if (joystick.getYButton()) {
      Robot.driveSubsystem.rightRearTalon.set(0.5);
    } else {
      Robot.driveSubsystem.rightRearTalon.set(0);
    }
  }

  @Override
  protected void end() {
    Robot.driveSubsystem.leftFrontTalon.set(0);
    Robot.driveSubsystem.leftRearTalon.set(0);
    Robot.driveSubsystem.rightFrontTalon.set(0);
    Robot.driveSubsystem.rightRearTalon.set(0);
  }

  @Override
  protected void interrupted() {
    end();
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
