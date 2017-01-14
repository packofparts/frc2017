package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Austin Jenchi (timtim17)
 */
public class FieldOrientedMecanumDriveCommand extends Command {

  public FieldOrientedMecanumDriveCommand() {
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    XboxController joystick = Robot.oi.getJoystick();
    System.out.println(joystick.getX(GenericHID.Hand.kLeft));
    Robot.driveSubsystem.mecanumDrive(joystick.getX(GenericHID.Hand.kLeft),
            joystick.getY(GenericHID.Hand.kLeft),
            joystick.getTriggerAxis(GenericHID.Hand.kRight) - joystick.getTriggerAxis(GenericHID.Hand.kLeft),
//            0);
            Robot.driveSubsystem.getAngle());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    // does nothing - this command will never end!
  }

  @Override
  protected void interrupted() {
    end();
  }
}
