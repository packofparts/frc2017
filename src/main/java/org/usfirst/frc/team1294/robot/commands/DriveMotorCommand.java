package org.usfirst.frc.team1294.robot.commands;

import org.usfirst.frc.team1294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Austin Jenchi (timtim17)
 */
public class DriveMotorCommand extends Command {
  private int i;

  public DriveMotorCommand(int i) {
    super("Drive motor " + i);
    this.i = i;
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    switch (i) {
      case 1:
        Robot.driveSubsystem.leftFrontTalon.set(0.5);
        break;
      case 2:
        Robot.driveSubsystem.leftRearTalon.set(0.5);
        break;
      case 3:
        Robot.driveSubsystem.rightFrontTalon.set(0.5);
        break;
      case 4:
        Robot.driveSubsystem.rightRearTalon.set(0.5);
        break;
    }
  }

  @Override
  protected void end() {
    Robot.driveSubsystem.robotDrive.drive(0, 0);
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
