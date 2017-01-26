package org.usfirst.frc.team1294.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveMotorCommand extends Command {
	
	  public final double HEADING_TO_TRAVEL_C = 0;
	  public final double DISTANCE_TO_TRAVEL_C = 1.99;
	  public final double HEADING_TO_FACE_C = 0;

	  public final double DISTANCE_TO_TRAVEL_R = 4.8;
	  public final double HEADING_TO_TRAVEL_R = 30.0;
	  public final double HEADING_TO_FACE_R = -90.0;

	  public final double DISTANCE_TO_TRAVEL_L = 4.8;
	  public final double HEADING_TO_TRAVEL_L = -30.0;
	  public final double HEADING_TO_FACE_L = 90.0;

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
